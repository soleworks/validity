# Add the constraints of a country

Follow an existing country, such as Brazil, file by file.

1. Create `<Country>Messages` in `io.github.soleworks.validity`, next to `BrazilMessages.kt`
   ([message/conventions.md](../../message/conventions.md#the-messages-of-a-country)).
2. In `Messages.kt`, add `internal val <country>` and `public fun <country>(block)`, in alphabetical
   order with the other countries.
3. Create `constraints/<country>/<Country>Constraints.kt` and its test
   `constraints/<country>/<Country>ConstraintsTest.kt`.
4. Add each constraint with [add-constraint.md](add-constraint.md), steps 3 to 4.
5. Update the counts in the README's "Batteries included" line (constraints, national documents).
6. Run `./gradlew updateKotlinAbi`, then `./gradlew build`.
