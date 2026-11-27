---
name: validity
description: Write, review and translate validation rules in Kotlin with Validity (io.github.soleworks:validity-core). Use when a Kotlin project validates objects, requests or forms with Validity, or when asked to add rules, constraints, messages or tests for them.
license: Apache-2.0
compatibility: Kotlin 2.4 on the JVM, Java 17 or newer. Phone numbers need io.github.soleworks:validity-phone, validating the request bodies of a Ktor server needs io.github.soleworks:validity-ktor, and those of a Spring Boot application need io.github.soleworks:validity-spring.
metadata:
  author: soleworks
  docs: https://validity-kt.dev
---

# Validity

Validity validates Kotlin objects with a DSL and reports every broken rule at once. Each `Violation` has a `path`
(`items[1].quantity`), a `message` for people and a `code` for programs (the name of the constraint, such as `minLength`).

Before writing rules, search these docs (MCP server `https://validity-kt.dev/mcp`) or read the page you need as
Markdown: `https://validity-kt.dev/<page>.md`. The constraints are listed by type of value under
`/constraints/<type>.md` (`strings`, `numbers`, `collections`, `dates-and-times`, `internet`, `countries`, ...).

## Writing rules

A class that owns its rules implements `Validatable`:

```kotlin
data class User(val name: String?, val email: String?, val age: Int?) : Validatable {
    override fun validation() = validation {
        ::name required { minLength(2) }
        ::email required { email() }
        ::age ifPresent { min(18) }
    }
}

user.validate()            // suspend, returns ValidationResult
user.validateBlocking()    // outside a coroutine
user.validateOrThrow()     // throws ValidationException with every violation
```

- Every property starts with what `null` means: `required` (null is a violation), `ifPresent` (null is valid) or
  `forbidden()` (only null is valid). Inside their block the value is non-null.
- Conditions are plain Kotlin: `if (type == "COMPANY") ::companyName required { notBlank() }`.
- Nested objects: `::shipping required { valid() }`. Collections: `::items required { each { valid() } }`.
- The rules of a parent class, at the same level: `include(super.validation())`. The rules of a parent type written with
  `validation<T> { }`: `include(personValidation)`.
- One subtype of a value whose type is not yours: `::payment required { ifInstanceOf<Payment.Card> { valid(cardRules) } }`.
  Another type, or `null`, skips the block.
- A class that is not yours: `val rules = validation<Address> { ::street required { notBlank() } }`, then
  `rules.validate(address)` or `::address required { valid(rules) }`.
- Rules that need caller data: implement `ContextualValidatable<C>`, `validation(context: C)`, and call
  `validate(context)`. Pass it down with `valid(context)`.
- Across fields: `exactlyOneOf(::pixKey, ::iban)`, `atLeastOneOf`, `allOrNoneOf`, `atLeastOf(2, ...)`, and
  `constraint(::total, message = "...", code = "...") { total == items.sumOf { it.price } }`.
- Custom rule once: `constraint("is out of stock", code = "stock") { it in stock }` inside a property block.
  Reusable: `fun ValidationNode<String>.sku(message: String = "...") = constraint(message, code = "sku") { ... }`.
- Text that carries a value: `::page ifPresent { convertToInt { min(1) } }`, `convertToLocalDate`, `convertToEnum<E> { }`.

## Imports

```kotlin
import io.github.soleworks.validity.*                     // Validatable, validation, required, each, valid, ...
import io.github.soleworks.validity.constraints.*         // email, minLength, positive, past, ...
import io.github.soleworks.validity.constraints.brazil.*  // cpf, cnpj, cep: each country has its own package
import io.github.soleworks.validity.phone.*               // phone, mobilePhone (validity-phone module)
import io.github.soleworks.validity.ktor.*                // ValidityPlugin (validity-ktor module)
import io.github.soleworks.validity.spring.*              // OnInvalid (validity-spring module)
```

## Mistakes that compile

- **Work outside a rule inside `failFast` always runs.** `failFast` skips rules, not code: put a lookup inside
  `constraint { }`, after the format rule it depends on.
- **Country constraints are not in `constraints.*`.** `cpf()` is `io.github.soleworks.validity.constraints.brazil.cpf`.
- **The message property is not always named after the constraint.** Map sizes use `minEntries`/`maxEntries`/`entries`/
  `entriesBetween`, collection `contains` uses `containsElement`, date `between` uses `betweenDates`, `isTrue` uses
  `mustBeTrue`. The code of the violation is always the constraint name.
- **`null` arguments skip a rule.** `min(null)`, `equalTo(null)` and `postalCode(null)` check nothing.
- **Rules about "now" read the configured clock.** In custom rules use `now(LocalDate::now)`, never `LocalDate.now()`,
  and fix the clock in tests with `validate(Validity { clock = Clock.fixed(...) })`.
- **Give custom rules a code.** A `constraint` without `code` reports an empty code.

## Messages

The messages are built in for `Language.EN` (the default), `Language.ES` and `Language.PT_BR`: pick one with
`Validity.configure { language = Language.PT_BR }`, or per call with `validate(language = Language.ES)`.
Change defaults once with `Validity.configure { messages { minLength = "nome curto" } }`, per
call with `validate(Validity { messages { ... } })`, or per rule with `minLength(2, message = "...")`. Countries have
blocks (`brazil { cpf = "..." }`) and the phone module has `phone { ... }`. Placeholders: `{path}`, `{value}` and the
arguments by name (`{min}`, `{max}`, `{other}`, `{fields}`).

## Tests

Assert on whole violations, which have value equality:

```kotlin
user.validateBlocking().violations shouldBe listOf(
    Violation(path = "email", message = "must be a valid email", code = "email")
)
```
