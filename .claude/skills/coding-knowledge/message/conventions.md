# Message conventions

## A messages class

A messages class is named `…Messages`, annotated `@ValidityDsl`, and has an `internal` constructor: only
the library creates it, and the user reaches it inside `Validity.configure { messages { … } }`. Each
public property is a `var` of type `String`, one text the user can change, with KDoc naming the
constraint it belongs to.

```kotlin
@ValidityDsl
public class BrazilMessages internal constructor() {
    /** Default message of `cpf`. */
    public var cpf: String = "must be a valid CPF"
}
```

Why: the configuration is the only door to the messages. Checked by the harness
(`ProductionConventionsTest`, Layout).

## The text

The text is in English, lower case, and completes a sentence about the value: `must be a valid CPF`,
`is required`. A placeholder in braces is filled when the violation is built: `{path}` and `{value}`
always, and the constraint's own parameters by name (`{min}`, `{fields}`, `{pattern}`).

## The messages of a country

Each country has a class `<Country>Messages`, held by `Messages` as `internal val <country>` and
opened with `public fun <country>(block: <Country>Messages.() -> Unit)`. Its constraints default to
`messages.<country>.<key>`.

## The messages of a module

A module does not change `Messages`. It declares its class, a private `MessagesKey` for it, an internal
accessor, and a public extension `Messages.<module>(block)`:

```kotlin
private val PHONE_MESSAGES = MessagesKey(::PhoneMessages)

internal val phoneMessages: PhoneMessages
    get() = PHONE_MESSAGES.current

public fun Messages.phone(
    block: PhoneMessages.() -> Unit
): Unit = module(PHONE_MESSAGES).block()
```

Why: the core does not depend on its modules.
