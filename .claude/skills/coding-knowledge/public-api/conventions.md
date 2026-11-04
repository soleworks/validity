# Public API conventions

## Changing the public API

A change that adds, removes or changes a public declaration runs `./gradlew updateKotlinAbi` and
commits the new `api/<module>.api` with the code, so the review shows the change.

Why: `./gradlew check` fails when the code and the dump differ.

## A breaking change

A change that removes or changes a line of the dump breaks code written against the previous version.
Its commit takes a `!` and a `BREAKING CHANGE:` footer that says what the user changes:

```text
feat(core)!: name the rules of an object SelfValidation

BREAKING CHANGE: Validation is now SelfValidation.
```

Why: the footer decides the next version, and CI fails a pull request whose dump lost a line without it
(`.github/scripts/check-commits.sh`). Prefer adding beside the old declaration to changing it, when the
old one can stay.

## Commits

Every commit is a Conventional Commit, with the module as scope: `feat(core)`, `fix(phone)`. `feat`,
`fix`, `perf` and `revert` reach the changelog (the defaults of release-please); `docs`, `test`, `refactor`, `build`, `ci` and `chore` change
nothing a user sees. CI checks every commit of a pull request.
