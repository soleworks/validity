# Constraint conventions

## The shape

A constraint is a public extension function on `ValidationNode<T>` that returns `Unit` and calls
`constraint(...)`, or another overload of itself that does.

```kotlin
public fun ValidationNode<Boolean>.isTrue(
    message: String = messages.mustBeTrue
): Unit = constraint(
    message = message,
    code = "isTrue",
    predicate = { it }
)
```

Why: the user calls it as a word inside a block (`::acceptedTerms required { isTrue() }`), and every
constraint reports the same way. Checked by the harness (`ProductionConventionsTest`, Constraints).

## Parameters

The parameters of the rule come first (`min`, `pattern`), then `message`, then only trailing lambdas.
`message` defaults to the configured messages (`messages.<key>`, or `messages.<country>.<key>`), never to
a literal.

Why: the user can change every default message in one place (see
[message/index.md](../message/index.md)). Checked by the harness.

## The code

The code is the function's name (`"cpf"`). Overloads that check different things take distinct codes
(`length`, `lengthBetween`). The code never changes once released: users match on it.

## Where it goes

- A rule on a kind of value goes in the file of that kind in `constraints/`. When the same rule applies
  to several types (`Int`, `Long`, `BigDecimal`), it has one overload per type in that file.
- A rule of one country goes in `constraints/<country>/<Country>Constraints.kt`; the file is named after
  the country (checked by the harness).
- An `internal` helper used by several files, such as `digits()` or `weightedSum()`, goes in
  `constraints/Helpers.kt`.
