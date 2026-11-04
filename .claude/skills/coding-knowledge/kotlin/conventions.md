# Kotlin conventions

## The written rule wins over the code around it

When existing code contradicts a rule of this base, follow the rule in what you write, change only
what the task covers, and list the divergence at the end of the delivery.

Why: copying a neighbor spreads a divergence; listing it lets the maintainer decide.

## Comments and KDoc

Production code carries no `//` or block comments. Every public declaration has KDoc, written as the
**kdoc** skill says: invoke it before adding or changing one.

Why: the name says what the code does, and the KDoc is what the user reads in the IDE; the first paragraph
of a constraint's KDoc is also its sentence on the docs site. Code that is not public needs no KDoc: a
comment there would only repeat the name. Detekt fails `./gradlew check` on a public declaration without KDoc, or with `@param` tags that no
longer match the signature.

## Visibility

The modules use explicit API mode: every declaration states `public` or `internal`, and a public one
states its return type. What a user does not call is `internal`. An `internal` member that a public
inline function reads takes `@PublishedApi`.

Why: everything public is a promise kept across versions (see
[public-api/index.md](../public-api/index.md)).

## Files

A file declares at most one top-level class, interface or object, named after it. A sealed type keeps
its subtypes nested in it. Functions of one kind go together in a file named after their kind, such as
`StringConstraints.kt`.

Why: the reader finds a type by its name. Checked by the harness (`ProductionConventionsTest`, Layout).

## Top-level values

A file declares its constants before its other values. A top-level value is named in upper snake
case. A regex is named after the format it matches (`CPF_FORMAT`), and a list of weights after the
check it weights (`CPF_WEIGHTS`).

```kotlin
private const val MODULUS = 11

private val CPF_FORMAT = Regex("""\d{11}|\d{3}\.\d{3}\.\d{3}-\d{2}""")
private val CPF_WEIGHTS = (11 downTo 2).toList()
```

Why: the name says what the value holds without reading it. Checked by the harness (Names, Layout).

## A lambda the user writes

Every predicate and message the user passes, and the block of `validation`, is a `suspend` lambda, so a
rule can call a repository or a client directly. A block of rules (`required`, `failFast`, `or`, ...) is the
lambda of an `inline` function instead, so it can call `suspend` functions too. The same function serves suspending and plain
lambdas: there is no separate suspend-only name. A blocking variant of an entry is named
`…Blocking`.

```kotlin
public fun constraint(
    message: String,
    code: String = "",
    predicate: suspend (V) -> Boolean
): Unit
```

Why: a user who validates against a database should not need a second API.

## Nothing speculative

Write only what the task uses: no parameter, overload or type for a case nobody asked for.

Why: every public addition is kept for good once released.
