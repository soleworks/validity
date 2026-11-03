# Add a constraint

1. Check that no constraint already does it, under another name or as an overload.
2. Add its default message ([message/recipes/add-message.md](../../message/recipes/add-message.md)).
3. Write its tests first ([testing/conventions.md](../../testing/conventions.md#testing-a-constraint)),
   and see them fail.
4. Write the constraint in the file it belongs to ([conventions.md](../conventions.md)), with its KDoc
   (the **kdoc** skill).
5. Run `./gradlew updateKotlinAbi` and keep the new lines of the dump
   ([public-api/conventions.md](../../public-api/conventions.md)).
6. Add its section to the page of its kind in `docs/constraints/`, in the topic it belongs to, written
   like the sections around it: a `` ### `name` `` heading, the call and its types, what it checks from
   its KDoc, and the line with its code, default message and the message to change. Add it to the
   table at the top of the page too. Checked by the harness (`DocsConventionsTest`).
7. Run `./gradlew build`.
