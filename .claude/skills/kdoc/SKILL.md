---
name: kdoc
description: KDoc conventions for the public API of validity-core and validity-phone. Use whenever you add, rename or change a public function, class, property or constraint in validity-*/src/main (a new constraint, a new overload, a new message, a new country module), and when Detekt reports UndocumentedPublicClass, UndocumentedPublicFunction, UndocumentedPublicProperty or OutdatedDocumentation.
---

# KDoc on the public API

Every public declaration in `validity-*/src/main` has KDoc. The IDE shows it while people type a rule, and the docs
site is generated from it with Dokka. Detekt enforces it: `UndocumentedPublicClass`, `UndocumentedPublicFunction` and
`UndocumentedPublicProperty` fail `./gradlew check`, and `OutdatedDocumentation` fails when the `@param` tags stop
matching the signature.

Write the KDoc in the same change as the declaration, never as a follow-up.

## What a constraint's KDoc says

1. **One sentence on what passes**, starting with "Checks that". Name the parameters as links: `[min]`.
2. **What it accepts**, when the name alone does not say it: the formats, separators, check digits, whether case or
   whitespace matters. Read the predicate and the regex, and describe what they do, not what the standard says.
3. **When nothing is checked**, if a nullable argument is `null` (`if (min == null) return`): "When [min] is `null`,
   nothing is checked."
4. **The violation**: its code and its default message, linked to the `Messages` property it comes from.
5. **An example**, when the call is not obvious: a parameter with a non-obvious meaning, a conversion, a block.
6. **`@param` for every parameter, type parameters included**, in declaration order. `OutdatedDocumentation` checks the
   names and the order. A `@param` is a short phrase ending with a period.

```kotlin
/**
 * Checks that the string has at least [min] characters.
 *
 * Fails with code `minLength` and, by default, the message `must have at least {min} characters`
 * ([Messages.minLength][io.github.soleworks.validity.Messages.minLength]).
 *
 * @param min the fewest characters accepted.
 * @param message the message of the violation. `{min}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.minLength(
    min: Int,
    message: String = messages.minLength
): Unit = ...
```

The overload that takes a message builder says what the builder receives, and does not repeat the default message:

```kotlin
/**
 * Checks that the string has at least [min] characters, with a message built from the value.
 *
 * Fails with code `minLength`.
 *
 * @param min the fewest characters accepted.
 * @param message builds the message from the value (the receiver) and [min].
 */
public fun ValidationNode<String>.minLength(
    min: Int,
    message: String.(Int) -> String
): Unit = ...
```

The same constraint for another receiver type (`Int`, `Long`, `BigDecimal`, `LocalDate`, `Instant`, ...) gets the
same text, adjusted to the type where it matters ("the date", "the collection or array").

## Other declarations

- **Classes and interfaces**: what they are for and how a user meets them. Constructor properties are documented with
  `@property` in the class KDoc, not with a KDoc on the parameter.
- **Properties of a `*Messages` class**: one line, the constraints it is the default message of (their names are also
  the codes of their violations), and the placeholders the text takes.

  ```kotlin
  /** Default message of `minLength`. Takes `{min}`. */
  public var minLength: String = "must have at least {min} characters"
  ```
- **Enum entries**: one line each, what the entry stands for.
- **DSL entry points** (`validation`, `required`, `each`, `valid`, `convert*`, `or`, `failFast`, `metadata`, ...): an
  example is expected, since they are what people copy.

## Style

- English, present tense, plain words. Sentences end with a period.
- Links: a declaration in the same package or imported by the file is linked by its name (`[Violation]`). Anything else
  takes its fully qualified name, with a short label when it would be too long to read:
  `[Messages.min][io.github.soleworks.validity.Messages.min]`.
- Code, placeholders and literal values go in backticks: `` `minLength` ``, `` `{min}` ``, `` `null` ``.
- Examples are a fenced `kotlin` block inside the KDoc, written the way the README writes rules
  (`::email required { email() }`).
- Do not restate the type of a parameter or the obvious ("@param message the message"). Say what the value means.
- Keep it short. A KDoc that is longer than the function needs a reason.

## Check

```bash
./gradlew detektMain
```

`detektMain` (with type resolution) is the one that runs these rules. The plain `detekt` task is disabled in this
build.
