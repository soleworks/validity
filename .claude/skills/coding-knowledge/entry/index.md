# Entry

## Overview

An entry is how a user starts a validation. There is one engine, `ValidationNode` and its rules, and
several entries into it:

- `Validatable`: the class declares its own rules, in `validation()`.
- `ContextualValidatable<C>`: the same, with a context the caller passes (a repository, the user).
- `validation<T> { }`: a function that builds the rules of a type from outside it, for a class the
  user cannot change.

Each entry offers `validate()` (returns a `ValidationResult`), `validateOrThrow()` (throws
`ValidationException`), and a `…Blocking` variant of each for code that is not suspending.

## Concepts

| Concept | Where |
|---|---|
| One engine, many entries | [conventions.md](conventions.md#one-engine-many-entries) |
| The four ways to run an entry | [conventions.md](conventions.md#the-four-ways-to-run) |
| The result and the violation | [conventions.md](conventions.md#the-result) |
| Tests of an entry | [testing/conventions.md](../testing/conventions.md#samples-the-readme-and-the-docs) |
