---
name: coding-knowledge
description: How Validity's code is built and changed, one folder per component (constraint, message, entry, module), with the Kotlin rules, the tests and the public API. Use before writing or changing code in validity-core, validity-phone or another validity-* module.
---

The knowledge for changing Validity, one folder per component, in `${CLAUDE_SKILL_DIR}`. Every folder
starts with `index.md`: an overview of the component and a table of its concepts, each pointing to the
file that covers it. In a folder, `conventions.md` holds the rules, which hold always. A recipe, in a
folder's `recipes/`, gives the steps of an action.

Most rules here are also checked by the build: the `harness` module (Konsist), ktlint (`.editorconfig`),
Detekt and the ABI dump.
A rule a check holds says which check, so a failure points back to its rule.

1. Name the components you are about to create or change.
2. Read `kotlin/index.md`, `testing/index.md` and the `index.md` of each of those components.
3. Read, whole, the files of the concepts the task involves, and the recipe of each action you
   perform.
4. Before writing each part, read again the files of its concepts: those of `testing/` before a test,
   those of the component before its code.

## Folders

- `kotlin/`: what every Kotlin file follows: names, files, constants, suspend lambdas, KDoc.
- `testing/`: where a test lives, its format, and how to run the checks.
- `constraint/`: a rule a value must pass, such as `email()` or `cpf()`.
- `message/`: the default text of a violation, and how a user changes it.
- `entry/`: how a user starts a validation: `Validatable`, `ContextualValidatable`, `validation<T>`.
- `module/`: a published module beside `validity-core`, such as `validity-phone`.
- `public-api/`: what a user compiles against, and what a change to it costs.
