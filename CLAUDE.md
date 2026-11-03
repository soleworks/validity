# Validity

Read `README.md` before changing what a user writes, and `RELEASING.md` before changing the build, the
version or the public API.

## Before writing code

Load the `coding-knowledge` skill. Before changing the docs site in `docs/`, read its `docs-site/` folder.

### One pattern per thing

The same thing looks and works the same everywhere: in the code, in the docs and in the site. When
something already exists for a job (a function, a type, a snippet, a style, a page structure), reuse it;
when a second form of the same job appears (a code group beside a code block, an overload beside a
function), it extends the first instead of starting its own rules. Before adding something, look for what
already does that job. When two places do the same job differently, make them one, or say why they differ.

## Done

NEVER say a task is done before `./gradlew build` passes: it runs the tests, the harness, ktlint, Detekt,
the coverage floor and the ABI check. After every edit of a Kotlin file, a hook formats it with ktlint and
returns what ktlint could not fix: fix those by hand, following `.editorconfig`.

The end-to-end proof of a change is a sample in `validity-core/src/test/.../samples/` that uses it the way
a user does, with a test that checks its result.

A change to `docs/` is done when `npm run docs:check` and `vale .` (in `docs/`) pass too, besides the build.
After every edit of a page or a stylesheet, a hook runs Vale, `mint validate`, Stylelint and the docs checks
of the harness on it and returns what they found: fix it before going on.
