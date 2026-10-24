<div align="center">

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/assets/logo-dark.svg">
  <source media="(prefers-color-scheme: light)" srcset="docs/assets/logo-light.svg">
  <img alt="Validity logo" src="docs/assets/logo-light.svg" width="160">
</picture>

<h1>Validity</h1>

**Validation for Kotlin, written in plain Kotlin.**

Every broken rule at once, each with a path, a message for people and a code for programs.

<br>

[![Build](https://img.shields.io/github/actions/workflow/status/soleworks/validity/ci.yml?branch=main&style=flat-square&logo=githubactions&logoColor=white&label=build)](https://github.com/soleworks/validity/actions/workflows/ci.yml)
[![Coverage](https://img.shields.io/codecov/c/github/soleworks/validity?style=flat-square&logo=codecov&logoColor=white&label=coverage)](https://codecov.io/gh/soleworks/validity)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.20-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Java](https://img.shields.io/badge/Java-17%2B-E76F00?style=flat-square&logo=openjdk&logoColor=white)](https://adoptium.net)
[![License](https://img.shields.io/badge/license-Apache%202.0-2F81F7?style=flat-square)](LICENSE)
[![Maven Central](https://img.shields.io/badge/Maven%20Central-coming%20soon-8B949E?style=flat-square&logo=apachemaven&logoColor=white)](#installation)

[Why Validity](#why-validity) · [Installation](#installation) · [Quick start](#quick-start) · [Modules](#modules) · [Documentation](#documentation)

</div>

<br>

## At a glance

```kotlin
enum class PaymentMethod { CARD, SEPA }

data class Payment(
    val method: String?,
    val amount: BigDecimal?,
    val currency: String?,
    val cardNumber: String? = null,
    val iban: String? = null
) : Validatable {
    override fun validation() = validation {
        ::method required { enum<PaymentMethod>() }
        ::amount required {
            positive()
            maxDecimalPlaces(2)
        }
        ::currency required { currencyCode() }

        when (method) {
            "CARD" -> ::cardNumber required { creditCard() }
            "SEPA" -> ::iban required { iban() }
        }
    }
}

val payment = Payment(method = "SEPA", amount = "-9.999".toBigDecimal(), currency = "EURO")

payment.validate().violations.forEach(::println)
```

It prints every broken rule, not just the first:

```text
Violation(path=amount, message=must be positive, code=positive)
Violation(path=amount, message=must have at most 2 decimal places, code=maxDecimalPlaces)
Violation(path=currency, message=must be a valid ISO 4217 currency code, code=currencyCode)
Violation(path=iban, message=is required, code=required)
```

## Why Validity

- **Rules read like the requirements.** `::email required { email() }` is the sentence from the ticket, written next
  to the data it protects.
- **Conditions are plain Kotlin.** "Required if", "only for companies" and "after the check-in" are an `if`, a `when`
  and a field of the object. No validation groups, no class-level annotations.
- **`null` is decided once.** `required`, `ifPresent` and `forbidden` settle what `null` means, and every constraint
  after them works on the non-null type, so autocomplete offers only the ones that fit.
- **Rules across fields are built in.** `atLeastOneOf`, `exactlyOneOf` and `allOrNoneOf` cover the rules that forms
  and payment APIs are full of, and `or`, `and` and `not` combine any constraints.
- **Nested objects and collections.** `valid()` and `each` follow the data, and every violation says where it is:
  `shipping.zipCode`, `items[1].quantity`, `prices[USD]`.
- **Every error at once, for two audiences.** A `message` for people, configurable and translatable, and a stable
  `code` for programs, plus the `metadata` you attach.
- **Batteries included.** More than 140 constraints, postal codes of 72 countries, national documents of 14, and phone
  numbers in their own module.
- **Nothing hidden.** No annotations, no code generation, no `kotlin-reflect`. `validity-core` depends on nothing but
  the Kotlin standard library.

How it compares, in each library's own terms:

| | Validity | Konform | Valiktor | Akkurate | Jakarta Validation |
|---|---|---|---|---|---|
| Rules are | Kotlin DSL | Kotlin DSL | Kotlin DSL | Kotlin DSL | annotations |
| They live | in the class, or beside it | in a `Validation<T>` | usually in `init` | in a `Validator<T>` | on fields and classes |
| A rule on another field | `if`, `when` | `dynamic { }` | code in the block | code in the block | class-level constraint, groups |
| Code generation | none | none | none | KSP plugin | none |
| Platforms | JVM 17+ | Multiplatform | JVM | Multiplatform | JVM |

Each of them is a solid choice, and some go where Validity does not yet: Konform and Akkurate run on Kotlin
Multiplatform, and Jakarta Validation plugs straight into the frameworks that read its annotations.

## Installation

> [!NOTE]
> Validity is not on Maven Central yet: the first release, `0.1.0`, is on its way. Until it lands, clone this
> repository next to your project and add `includeBuild("../validity")` to your `settings.gradle.kts`. Gradle then
> builds the dependencies below from the clone.

```kotlin
dependencies {
    implementation("io.github.soleworks:validity-core:0.1.0")

    // Optional: phone numbers, with Google's libphonenumber
    implementation("io.github.soleworks:validity-phone:0.1.0")
}
```

Validity runs on Java 17 or newer and is built with Kotlin 2.4.

## Quick start

Implement `Validatable`, declare the rules, and validate:

```kotlin
import io.github.soleworks.validity.*
import io.github.soleworks.validity.constraints.*

data class User(
    val name: String?,
    val email: String?,
    val age: Int?
) : Validatable {
    override fun validation() = validation {
        ::name required { minLength(2) }
        ::email required { email() }
        ::age ifPresent { min(18) }
    }
}

val result = User(name = "A", email = "ana@", age = 16).validate()

result.isValid      // false
result.violations   // every broken rule, in the order the rules are declared
```

```text
Violation(path=name, message=must have at least 2 characters, code=minLength)
Violation(path=email, message=must be a valid email, code=email)
Violation(path=age, message=must be at least 18, code=min)
```

At the edge of your system, `user.validateOrThrow()` throws a `ValidationException` that carries the same list, so one
error handler can turn every invalid request into a response.

## Modules

| Module | What it adds | Dependencies |
|---|---|---|
| `validity-core` | the DSL and every constraint but the phone ones | none |
| `validity-phone` | `phone`, `mobilePhone`, `countryCode` and `areaCode` | Google's [libphonenumber](https://github.com/google/libphonenumber) |

## Documentation

A documentation site is on its way, with every concept, the full list of constraints, messages and configuration.

## Development

`./gradlew build` compiles, tests and checks every module. Two tools keep the code in one style:

- **Detekt** runs every rule it has, the ktlint and libraries rule sets included. [config/detekt/detekt.yml](config/detekt/detekt.yml) lists the few rules this project tunes, each with its reason. `./gradlew detektMain detektTest -PautoCorrect=true` fixes the formatting it can.
- **Konsist**, in the internal `harness` module, checks the conventions Detekt cannot see: how a constraint is declared, how values are named, and that every test is a Given, a When and a Then, ordered by what it checks.

**Kover** measures what the tests cover in `validity-core` and `validity-phone`. `./gradlew :koverHtmlReport` writes the report to `build/reports/kover/html`, and CI uploads it to [Codecov](https://codecov.io/gh/soleworks/validity).

None of these tools, nor Kotest, is a dependency of a published module.

The examples in this README are samples in
[validity-core/src/test/.../samples/readme](validity-core/src/test/kotlin/io/github/soleworks/validity/samples/readme),
and a test checks the output shown next to them. When you change an example, change its sample too.

## License

Validity is released under the [Apache License 2.0](LICENSE).
