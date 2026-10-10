# Validity

Simple, idiomatic validation for Kotlin.

> **Work in progress.** The API is not stable and nothing is published yet.

```kotlin
enum class PaymentMethod { PIX, TED }

data class Transfer(
    val method: String?,
    val amount: BigDecimal?,
    val document: String?,
    val pixKey: String? = null,
    val agency: String? = null,
    val account: String? = null
) : Validatable {
    override fun validation() = validation {
        ::method required { enum<PaymentMethod>() }
        ::amount required {
            positive()
            maxDecimalPlaces(2)
        }
        ::document required {
            or {
                cpf()
                cnpj()
            }
        }

        when (method) {
            "PIX" -> ::pixKey required { maxLength(77) }
            "TED" -> allOrNoneOf(::agency, ::account)
        }
    }
}

Transfer(method = "DOC", amount = BigDecimal("10.001"), document = "123").validate().violations
// [Violation(path=method, message=must be one of PIX, TED, code=enum),
//  Violation(path=amount, message=must have at most 2 decimal places, code=maxDecimalPlaces),
//  Violation(path=document, message=must be a valid CPF or must be a valid CNPJ, code=or)]
```

- A class implements `Validatable` and returns its rules from `validation()`; `validate()` runs them.
- Every error is reported at once, each as a `Violation(path, message, code)`.
- Conditions are plain Kotlin: `if`, `when`, loops, and the other fields of the object.

## Contents

- [Running a validation](#running-a-validation)
- [Premises: how a field enters](#premises-how-a-field-enters)
- [Rules across fields](#rules-across-fields)
- [Nested objects and collections](#nested-objects-and-collections)
- [Constraints](#constraints)
- [Combining constraints](#combining-constraints)
- [Messages](#messages)
- [Configuration](#configuration)
- [Modules](#modules)

## Running a validation

```kotlin
val result = transfer.validate()

result.isValid      // false
result.violations   // [Violation(path=amount, message=..., code=maxDecimalPlaces), ...]
```

- `path` is where the error is (`amount`, `address.street`, `phones[1]`, `prices[USD]`).
- `message` is for people; it can be translated and configured (see [Messages](#messages)).
- `code` is for programs: the name of the constraint that failed (`minLength`, `cpf`, `required`, `or`).
  It does not change when the message does, so a front end can translate by `code`.

To stop on an invalid object, throw its violations and let one error handler turn them into a response:

```kotlin
val request = call.receive<CreateAccountRequest>().also { it.validateOrThrow() }
// or: request.validate().orThrow()
// ValidationException: username: 'al' is too short, use at least 3 characters
//   .violations == [Violation(path=username, message=..., code=minLength)]
```

A `validation { }` can also run by itself, without a class that implements `Validatable`:

```kotlin
val result = validation {
    required("document", document) { cpf() }
    ifPresent("page", page) { min(1) }
}.validate()
// document = null, page = 0
// [Violation(path=document, message=is required, code=required),
//  Violation(path=page, message=must be at least 1, code=min)]
```

That is the way to check values that are not properties, such as the query and path parameters of an endpoint:
the name passed to `required` / `ifPresent` becomes the path.

## Premises: how a field enters

A premise takes a field (`::name`) and says what `null` means for it.

| Premise | `null` | Otherwise |
|---|---|---|
| `::name required { }` | reports `is required` | runs the block |
| `::name.required("tell us your name") { }` | reports the given message | runs the block |
| `::nickname ifPresent { }` | accepted | runs the block |
| `::companyName.forbidden()` | accepted | reports `must be null` |

```kotlin
override fun validation() = validation {
    ::name.required("tell us your name") { minLength(2) }
    ::nickname ifPresent { minLength(3) }
}
```

Inside the block, the value is never `null`, so the constraints receive the real type (`String`, not `String?`).

## Rules across fields

Rules that depend on other fields read them directly, because the block runs inside the object:

```kotlin
data class Booking(val checkIn: LocalDate?, val checkOut: LocalDate?) : Validatable {
    override fun validation() = validation {
        ::checkIn required { futureOrPresent() }
        ::checkOut required { after(checkIn) }
    }
}
// [Violation(path=checkOut, message=must be after 2026-10-10, code=after)]
```

A constraint that compares with another value skips the check when that value is `null`; the other field's own
premise reports it.

Rules about which fields may be filled:

```kotlin
override fun validation() = validation {
    exactlyOneOf(::pixKey, ::bankAccount)
    atLeastOneOf(::email, ::phone)
    atMostOneOf(::cpf, ::cnpj)
    allOrNoneOf(::bank, ::agency, ::account)

    if (cpf != null) ::companyName.forbidden()
}
```

| Premise | Fails when | Reports on |
|---|---|---|
| `atLeastOneOf` | every field is `null` | every field |
| `atMostOneOf` | more than one field is filled | each filled field |
| `exactlyOneOf` | none, or more than one, is filled | every field / each filled field |
| `allOrNoneOf` | some, but not all, are filled | each empty field |

"Required if" is a Kotlin `if`: `if (type == PIX) ::pixKey required { }`.

## Nested objects and collections

```kotlin
data class Customer(
    val phones: List<String>?,
    val address: Address?,
    val prices: Map<String, Int?>?
) : Validatable {
    override fun validation() = validation {
        ::phones required { each { phone(countryCode = "55") } }
        ::address required { valid() }
        ::prices required {
            eachKey { length(3) }
            eachValue { notNull() }
        }
    }
}
```

- `each` runs the block on every item (`List`, `Set`, `Array`, or the entries of a `Map`); the path gets the index
  or the key: `phones[1]`, `prices[USD]`.
- `eachKey` / `eachValue` run it on the keys or the values of a `Map`.
- `valid()` runs the rules of a nested object that implements `Validatable`; its paths get the field as prefix:
  `address.street`.

### Classes you don't own

A class from another library (or generated code) cannot implement `Validatable`. Write its rules in a function with
the same body, and pass the function to `valid`:

```kotlin
fun Address.validation() = validation {
    ::street required { notBlank() }
    ::zipCode required { postalCode(country) }
}

::address required { valid(Address::validation) }
// [Violation(path=address.street, message=must not be blank, code=notBlank),
//  Violation(path=address.zipCode, message=must be a valid postal code for BR, code=postalCode)]
```

The same function validates the object by itself: `address.validation().validate()`.

> **Declare that function at the top level of a file.** Declared inside another class
> (`private fun Address.validation()` inside `Customer`), a property of that outer class such as `::name` also
> compiles in the rules and is reported as `address.name`. At the top level, it is a compile error.

## Constraints

Constraints are functions on the type of the value, so the autocomplete shows only the ones that apply:

| Type | Examples |
|---|---|
| any | `equalTo`, `notEqualTo`, `oneOf`, `noneOf`, `notNull` |
| `String` | `minLength`, `maxLength`, `length`, `notBlank`, `matches`, `contains`, `startsWith`, `digits`, `uppercase`, `enum<E>()` |
| numbers (`Int`, `Long`, `BigDecimal`...) | `min`, `max`, `between`, `positive`, `negativeOrZero`, `multipleOf`, `maxDecimalPlaces` |
| dates and times (`java.time`, `Date`) | `past`, `future`, `after`, `before`, `between` |
| `Duration` | `min`, `max`, `between` |
| collections and maps | `minSize`, `maxSize`, `notEmpty`, `distinct`, `contains`, `containsKey` |

Text formats: `email`, `url`, `uuid`, `ipv4`, `ipv6`, `hostname`, `json`, `base64`, `jwt`, `hexColor`, `iban`,
`bic`, `creditCard`, `isbn`, `isoDate`, `dateFormat`, `isoCountryCode`, `currencyCode`, `postalCode(country)` (72
countries) and more.

Documents of 44 countries, one package each, such as `constraints.brazil` (`cpf`, `cnpj` numeric and alphanumeric,
`cnh`, `pis`, `tituloEleitoral`, `chaveNfe`, `cep`, `placa`), `constraints.spain` (`dni`, `nie`, `nif`) and
`constraints.india` (`aadhaar`, `pan`).

A rule of your own is a `constraint`, with an optional code:

```kotlin
::amount required {
    constraint("must be positive", code = "positive") { it > 0 }
}
```

Reusable ones are extension functions on `ValidationNode` that call `constraint`, like the built-in ones.

## Combining constraints

The constraints of a block must all pass. `or`, `and` and `not` combine them differently:

```kotlin
::document required {
    or {
        cpf()
        cnpj()
    }
}
// "123" → Violation(path=document, message=must be a valid CPF or must be a valid CNPJ, code=or)

::username required {
    not { oneOf(listOf("admin", "root")) }
}
// "root" → Violation(path=username, message=is not allowed, code=not)
```

| Function | Passes when | Default message |
|---|---|---|
| `or { }` | at least one rule passes | the messages of the rules, joined with "or" |
| `and { }` | every rule passes | the messages of the failing rules, joined with "and" |
| `not { }` | none of the rules passes | `is not allowed` |

Each reports one violation, with the code `or`, `and` or `not`. They nest (`or { cpf(); and { ... } }`) and take a
message of their own: `or("must be a CPF or a CNPJ") { ... }`.

## Messages

Every constraint has a default message in English. Pass your own where you need it, as text with the
constraint's placeholders or as a function of the rejected value:

```kotlin
::sku required { minLength(5, "must have at least {min} characters, like SKU-1") }
::username required { minLength(3) { min -> "'$this' is too short, use at least $min characters" } }
```

Any message can also use `{path}` and `{value}`.

To change a message everywhere, or translate the messages, configure them once at startup:

```kotlin
Validity.configure {
    messages {
        required = "{path} é obrigatório"
        minLength = "{path} precisa de pelo menos {min} caracteres"
        or = "{left} ou {right}"
        brazil {
            cpf = "CPF inválido"
            cnpj = "CNPJ inválido"
        }
        phone {
            phone = "telefone inválido"
        }
    }
}
// [Violation(path=document, message=CPF inválido ou CNPJ inválido, code=or),
//  Violation(path=pixKey, message=pixKey é obrigatório, code=required)]
```

A message passed to a function wins over the configured one. The messages of each country live in its own section
(`brazil { }`, `spain { }`...), and the phone module adds `phone { }`.

## Configuration

`Validity.configure { }` replaces the whole configuration: messages and the clock.

```kotlin
Validity.configure {
    clock = Clock.fixed(Instant.parse("2030-01-01T00:00:00Z"), ZoneOffset.UTC)
}
```

The clock is the "now" of `past`, `future`, `pastOrPresent`, `futureOrPresent` and of the documents that depend on
the current date; set a fixed one in tests. The default is the system clock.

## Modules

| Module | What it adds |
|---|---|
| `validity-core` | everything above; no dependencies |
| `validity-phone` | `phone`, `mobilePhone`, `countryCode`, `areaCode`, using Google's libphonenumber |

```kotlin
::phone required { phone(countryCode = "55", format = PhoneFormat.AREA_CODE_AND_NUMBER) }
```

## Requirements

Java 17+.

## License

[Apache License 2.0](LICENSE)
