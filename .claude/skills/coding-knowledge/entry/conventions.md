# Entry conventions

## One engine, many entries

A new way to start a validation is a new entry into the same engine, never a second engine: it builds
`ValidationNode`s and runs them like the others. `Validatable` is the entry for rules inside a class;
the entry for rules outside it is the `validation<T> { }` function, not a class the user extends.

Why: a constraint written once works in every entry, and the user learns one set of words.

## The four ways to run

An entry offers `validate()`, `validateOrThrow()`, `validateBlocking()` and
`validateOrThrowBlocking()`. The suspending ones are the main path; the blocking ones run the same
rules for a caller that cannot suspend. `validateOrThrow` is `validate().orThrow()`, never a second
path.

Why: rules can suspend ([kotlin/conventions.md](../kotlin/conventions.md#a-lambda-the-user-writes)), and
a caller that cannot suspend still needs an answer.

## The result

`validate()` returns a `ValidationResult` with every `Violation` found, in the order of the rules.
`validateOrThrow()` throws `ValidationException` carrying the same violations, and returns nothing
when there are none. A `Violation` has a `path`, a `message`, a `code` and the node's `metadata`.
