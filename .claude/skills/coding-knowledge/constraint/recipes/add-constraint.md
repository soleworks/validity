# Add a constraint

1. Check that no constraint already does it, under another name or as an overload, and whether
   `docs/research/constraint-gaps.md` proposes it: take its name, signature and message from there.
2. Add its default message ([message/recipes/add-message.md](../../message/recipes/add-message.md)).
3. Write its tests first ([testing/conventions.md](../../testing/conventions.md#testing-a-constraint)),
   and see them fail.
4. Write the constraint in the file it belongs to ([conventions.md](../conventions.md)), with its KDoc
   (the **kdoc** skill).
5. Run `./gradlew updateKotlinAbi` and keep the new lines of the dump
   ([public-api/conventions.md](../../public-api/conventions.md)).
6. Run `./gradlew build`.
