# Validity

Simple, idiomatic validation for Kotlin.

> **Work in progress.** The API is not stable and nothing is published yet.

```kotlin
data class Transfer(
    val method: String?,
    val amount: Int?,
    val pixKey: String?,
    val agency: String?,
    val account: String?,
) : ValidationScope {
    override fun validate() = validation {
        ::method required {}
        ::amount required { must(message = { "must be positive" }) { it > 0 } }

        when (method) {
            "PIX" -> ::pixKey required { maxLength(77) }
            "TED" -> {
                ::agency required { minLength(4); maxLength(4) }
                ::account required {}
            }
        }
    }
}

val result = Transfer(method = "TED", amount = 0, pixKey = null, agency = "12", account = null).validate()

result.isValid     // false
result.violations  // [Violation(field=amount, message=must be positive),
                   //  Violation(field=agency, message=must have at least 4 characters),
                   //  Violation(field=account, message=is required)]
```

- Implement `ValidationScope` with `override fun validate() = validation { ... }`; it returns a `ValidationResult`.
- `required` fails when the field is null; `ifPresent` only checks fields that have a value.
- Every constraint has a default message. Pass your own as the last argument where you need to: `minLength(2) { "too short" }`.
- Conditions are plain Kotlin: `if`, `when`, loops.

## Writing a constraint

```kotlin
class Cpf : Constraint<String>() {
    override fun check(value: String) = isValidCpf(value)
    override fun defaultMessage(value: String) = "invalid CPF"
}

fun Validatable<String>.cpf(message: (Cpf.(value: String) -> String)? = null) = Cpf().also { attach(it, message) }

// ::document required { cpf() }
// ::document required { cpf { "CPF inválido" } }
```

For a one-off check, use `must`: `::age required { must { it >= 18 } }`.

## Nested objects

```kotlin
data class Address(val street: String?) : ValidationScope {
    override fun validate() = validation { ::street required {} }
}

data class Customer(val address: Address?) : ValidationScope {
    override fun validate() = validation { ::address required { valid() } }
}

// Customer(address = Address(street = null)).validate().violations
// [Violation(field=address.street, message=is required)]
```

`valid()` runs the nested object's own `validate()` and prefixes its violations with the field. It only exists for fields whose type implements `ValidationScope`, so forgetting to implement it is a compile error, not a silent skip.

## Combining constraints

```kotlin
::document required { (cpf() or cnpj()) and onlyDigits() }
```

`or` and `and` can be nested. Like every Kotlin infix function they share one precedence and read left to right, so use parentheses. Return the constraint from your own functions (`Cpf().also { attach(it, message) }`) so they can be combined.

## Requirements

Java 17+.

## License

[Apache License 2.0](LICENSE)
