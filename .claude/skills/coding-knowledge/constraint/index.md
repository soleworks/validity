# Constraint

## Overview

A constraint is a rule a value must pass, called inside a validation block: `email()`, `minLength(3)`,
`cpf()`. It is an extension on `ValidationNode<T>` that adds a check with `constraint(...)`; the node
runs it and turns a failure into a `Violation` with a path, a message and a code.

General constraints live in `validity-core`, in `constraints/`, one file per kind of value
(`StringConstraints.kt`, `NumberConstraints.kt`). A constraint of one country lives in
`constraints/<country>/`. A constraint that needs a dependency lives in its own module (see
[module/index.md](../module/index.md)).

## Concepts

| Concept | Where |
|---|---|
| The shape of a constraint | [conventions.md](conventions.md#the-shape) |
| Its parameters | [conventions.md](conventions.md#parameters) |
| Its code | [conventions.md](conventions.md#the-code) |
| Which file it goes in | [conventions.md](conventions.md#where-it-goes) |
| Its message | [message/index.md](../message/index.md) |
| Its KDoc | the **kdoc** skill |
| Its tests | [testing/conventions.md](../testing/conventions.md#testing-a-constraint) |
| Add a constraint | [recipes/add-constraint.md](recipes/add-constraint.md) |
| Add the constraints of a country | [recipes/add-country.md](recipes/add-country.md) |
| Constraints proposed and not built yet | `docs/research/constraint-gaps.md` |
