# Testing conventions

Each rule here is checked by the harness (`TestConventionsTest`): a test that breaks one fails
`./gradlew build`.

## Where a test lives and its name

A test is in the same module and package as the file it tests, named after it with `Test`:
`constraints/BooleanConstraints.kt` has `constraints/BooleanConstraintsTest.kt`. A test file declares
one top-level class.

## The format

- Every test sits in a `@Nested inner class` named after the function it tests, with
  `@DisplayName("When {function} is called")`.
- In a nested class, tests go in this order of outcome: accepts, reports, skips, builds, throws.
- The name is `` `given {situation} should {outcome}` ``, with a real situation (`given declined terms`),
  not a value (`given false`).
- The body is a Given, one When and one Then, separated by blank lines. The When is
  `val violations = …`, `val result = …`, `val error = …` or `….validate()`; the Then is one assertion
  with Kotest (`shouldBe`). A test of an exception is `shouldThrowExactly<X> { }` alone (Detekt forbids `shouldThrow`, which
  also passes for a subclass of X).
- `Violation(...)` and `ValidationNode(...)` always name their arguments.

```kotlin
@Nested
@DisplayName("When isTrue is called")
inner class IsTrue {
    @Test
    fun `given declined terms should report them`() {
        val node = ValidationNode(path = "termsAccepted", value = false).apply { isTrue() }

        val violations = node.validate()

        violations shouldBe listOf(
            Violation(path = "termsAccepted", message = "must be true", code = "isTrue")
        )
    }
}
```

## Testing a constraint

A constraint test builds a `ValidationNode` with the constraint and validates it, as above. It never
validates a sample class: that tests the entry, not the constraint. Each constraint has at least a
test that accepts and one that reports with the default message, which also covers that message.

## Samples and the README

Tests of an entry validate the classes in `samples/`, written the way a user writes them. Each code
example of the README is a file in `samples/readme/`, so the README only shows code that compiles and
whose result a test checks. A change to the README's examples changes those files first.

## Running the checks

`./gradlew build` runs everything: the tests, the harness, ktlint, Detekt, the coverage floor and the ABI
check. Run the tests of
the module you change while working (`./gradlew :validity-core:test`), and `./gradlew build` once
before declaring the task done.
