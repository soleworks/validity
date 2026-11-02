# Validity

Read `README.md` before changing what a user writes, and `RELEASING.md` before changing the build, the
version or the public API.

## Before writing code

Load the `coding-knowledge` skill.

## Done

NEVER say a task is done before `./gradlew build` passes: it runs the tests, the harness, Detekt and the
ABI check. The end-to-end proof of a change is a sample in `validity-core/src/test/.../samples/` that
uses it the way a user does, with a test that checks its result.
