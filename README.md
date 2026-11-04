<div align="center">

<picture>
  <source media="(prefers-color-scheme: dark)" srcset="docs/logo/dark.svg">
  <source media="(prefers-color-scheme: light)" srcset="docs/logo/light.svg">
  <img alt="Validity logo" src="docs/logo/light.svg" width="160">
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
[![Maven Central](https://img.shields.io/static/v1?label=Maven%20Central&message=0.1.0&color=2F81F7&style=flat-square&logo=apachemaven&logoColor=white)](https://central.sonatype.com/artifact/io.github.soleworks/validity-core)

**[Documentation](https://validity-kt.dev)** · [Quick start](#quick-start) · [Installation](#installation) · [Why Validity](#why-validity)

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
- **Conditions are plain Kotlin.** "Required if" and "only for companies" are an `if` and a `when`. No validation
  groups, no class-level annotations.
- **`null` is decided once.** `required`, `ifPresent` and `forbidden` settle what `null` means, and every constraint
  after them works on the non-null type.
- **Every error at once, for two audiences.** A `message` for people, configurable and translatable, and a stable
  `code` for programs, each with the path where it happened.
- **Batteries included.** 221 constraints, postal codes of 72 countries, national documents of 36, and phone
  numbers in their own module.
- **Nothing hidden.** No annotations, no code generation, no `kotlin-reflect`.

The [documentation](https://validity-kt.dev) compares it with Konform, Valiktor, Akkurate and Jakarta Validation.

## Installation

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

`validate()` is a `suspend` function, so a rule can query a database or an API. Outside a coroutine, call
`validateBlocking()` instead.

At the edge of your system, `user.validateOrThrow()` (or `validateOrThrowBlocking()`) throws a `ValidationException`
that carries the same list, so one error handler can turn every invalid request into a response.

Going further:

- [Rules beside the class](https://validity-kt.dev/guides/beside-the-class), for a type that is not yours.
- [Stopping at the first failure](https://validity-kt.dev/guides/fail-fast), when a rule only makes sense after another.
- [Rules that need the caller's data](https://validity-kt.dev/guides/context), such as the logged-in user.
- [Rules that suspend](https://validity-kt.dev/guides/coroutines), such as a database lookup.

## Modules

| Module | What it adds | Dependencies |
|---|---|---|
| `validity-core` | the DSL and every constraint but the phone ones | [kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) |
| `validity-phone` | `phone`, `mobilePhone`, `countryCode` and `areaCode` | Google's [libphonenumber](https://github.com/google/libphonenumber) |

## Documentation

**[validity-kt.dev](https://validity-kt.dev)** has every concept, the full list of constraints, the
messages and the configuration.

It is written for AI agents too: every page is also Markdown (add `.md` to its address), an index of every page
is in [`/llms.txt`](https://validity-kt.dev/llms.txt) and the whole site in
[`/llms-full.txt`](https://validity-kt.dev/llms-full.txt), and an MCP server lets Claude, Cursor or VS Code search it:

```bash
claude mcp add --transport http validity https://validity-kt.dev/mcp
```

## Contributing

[CONTRIBUTING.md](CONTRIBUTING.md) explains how to build the project, the checks every change passes and how the docs
site is made.

## License

Validity is released under the [Apache License 2.0](LICENSE).
