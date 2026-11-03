# Add the constraints of a country

Follow an existing country, such as Brazil, file by file.

1. Create `<Country>Messages` in `io.github.soleworks.validity`, next to `BrazilMessages.kt`
   ([message/conventions.md](../../message/conventions.md#the-messages-of-a-country)).
2. In `Messages.kt`, add `internal val <country>` and `public fun <country>(block)`, in alphabetical
   order with the other countries.
3. Create `constraints/<country>/<Country>Constraints.kt` and its test
   `constraints/<country>/<Country>ConstraintsTest.kt`.
4. Add each constraint with [add-constraint.md](add-constraint.md), steps 3, 4 and 6: its section goes
   in `docs/constraints/countries.mdx`, under a `## <Country> · `constraints.<country>`` heading.
5. Update the counts in the README's "Batteries included" line (constraints, national documents), and
   the same counts in the docs: the stats of `docs/index.mdx`, the descriptions of
   `docs/constraints/overview.mdx` and `docs/constraints/countries.mdx`, the list of blocks in
   `docs/guides/messages.mdx#messages-of-a-country` and the countries of `docs/reference/modules.mdx`.
6. Run `./gradlew updateKotlinAbi`, then `./gradlew build`.
