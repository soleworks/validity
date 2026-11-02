# Message

## Overview

Every constraint reports a message. Its default text is a property of a messages class, and the user
changes it once, for the whole application:

```kotlin
Validity.configure { messages { required = "obrigatório"; brazil { cpf = "CPF inválido" } } }
```

`Messages` holds the general texts and one class per country (`BrazilMessages`). A module beside the
core adds its own class through a `MessagesKey` (`PhoneMessages`).

## Concepts

| Concept | Where |
|---|---|
| A messages class | [conventions.md](conventions.md#a-messages-class) |
| The text and its placeholders | [conventions.md](conventions.md#the-text) |
| The messages of a country | [conventions.md](conventions.md#the-messages-of-a-country) |
| The messages of a module | [conventions.md](conventions.md#the-messages-of-a-module) |
| Add a message | [recipes/add-message.md](recipes/add-message.md) |
