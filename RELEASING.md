# Releasing

Validity follows [Semantic Versioning](https://semver.org). While the version is `0.x` the API may still change, and a
breaking change raises the minor version. A release candidate, such as `1.0.0-RC1`, comes before `1.0.0` and before
each new major version.

A release comes out when the maintainers decide, not on every merge: a version on Maven Central can never be changed or
removed.

## How a version is born

1. **Commits say what they change**, in the [Conventional Commits](https://www.conventionalcommits.org) form the
   history already uses: `feat(core): ...` adds something, `fix(phone): ...` corrects something. A change that breaks
   code written against the previous version takes a `!` (`feat(core)!: ...`) and a `BREAKING CHANGE:` footer that
   says what to change.
2. **The Release workflow keeps a release pull request open.** On every push to `main`,
   [release-please](https://github.com/googleapis/release-please) updates it with the next version, worked out from the
   commits, and the entries of `CHANGELOG.md`. It also sets the version in `gradle.properties` and in the installation
   snippet of the README.
3. **Merging that pull request is the release.** release-please tags the merge commit (`v0.2.0`) and creates the
   GitHub release with its changelog. The same workflow then builds the tagged commit and publishes `validity-core`
   and `validity-phone`, signed, to Maven Central.

The release pull request is opened with the workflow's own token, so it does not start the CI workflow. It only
changes the changelog and the version, and CI runs again on `main` after the merge.

To correct the changelog of a merged pull request, such as a breaking change committed without its `!`, add a
`BEGIN_COMMIT_OVERRIDE` block with the right message to the body of that pull request, before the next release.

## The public API

`api/<module>.api` holds the public API of each published module, and `./gradlew check` fails when the code no
longer matches it. A pull request that changes the API on purpose runs `./gradlew updateKotlinAbi` and commits the
new dump, so the review shows the change, and a removed or changed declaration gets the `!` of a breaking change.

## One-time setup

The Release workflow reads four repository secrets:

| Secret | What it is |
|---|---|
| `MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD` | A user token of the [Central Portal](https://central.sonatype.com) account that owns the `io.github.soleworks` namespace |
| `SIGNING_KEY` | The ASCII-armored private GPG key that signs the artifacts, with its public key on a keyserver |
| `SIGNING_KEY_PASSWORD` | The passphrase of that key |
