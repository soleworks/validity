# Module

## Overview

`validity-core` has no dependency besides the Kotlin standard library and coroutines. A group of
constraints that needs another library, such as libphonenumber, goes in a module of its own,
`validity-<name>`, published with the core and at the same version. `validity-phone` is the model.

## Concepts

| Concept | Where |
|---|---|
| What a module holds | [conventions.md](conventions.md#what-a-module-holds) |
| Its messages | [message/conventions.md](../message/conventions.md#the-messages-of-a-module) |
| Create a module | [recipes/create-module.md](recipes/create-module.md) |
