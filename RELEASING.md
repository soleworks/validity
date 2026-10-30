# Releasing

Validity follows [Semantic Versioning](https://semver.org). While the version is `0.x` the API may still change, and a
breaking change raises the minor version. From `1.0.0` on, it raises the major version.

A release comes out when the maintainers decide, not on every merge: a version on Maven Central can never be changed or
removed.

## How a version is born

1. **Commits say what they change**, as [Conventional Commits](https://www.conventionalcommits.org):
   `feat(core): ...` adds something, `fix(phone): ...` corrects something, and `docs`, `test`, `refactor`, `build`,
   `ci` and `chore` change nothing a user sees. A change that breaks code written against the previous version takes a
   `!` and a footer that says what to change:

   ```text
   feat(core)!: name the rules of an object SelfValidation

   BREAKING CHANGE: Validation is now SelfValidation.
   ```

2. **The Release workflow keeps a release pull request open.** On every push to `main`,
   [release-please](https://github.com/googleapis/release-please) updates it with the next version, worked out from the
   commits, and the entries of `CHANGELOG.md`: `feat` and `fix` commits, and every breaking change. It also sets the
   version in `gradle.properties` and in the installation snippet of the README.
3. **Merging that pull request is the release.** release-please tags the merge commit (`v0.2.0`) and creates the
   GitHub release with its changelog. The same workflow then checks that the build has that version, builds it, and
   publishes `validity-core` and `validity-phone`, signed, to Maven Central.

## What CI checks on every pull request

- **Every commit is a Conventional Commit.** A commit release-please cannot read would be left out of the changelog.
- **The public API matches its dump.** `api/<module>.api` holds the public API of each published module, and
  `./gradlew check` fails when the code no longer matches it. A pull request that changes the API on purpose runs
  `./gradlew updateKotlinAbi` and commits the new dump, so the review shows the change.
- **A break is declared.** When the dump loses or changes a line, one of the commits needs the `!` and the
  `BREAKING CHANGE:` footer, so the version cannot go up by less than it should.

The script behind the last two commit checks is [.github/scripts/check-commits.sh](.github/scripts/check-commits.sh).

## Other versions

- **A release candidate**, such as before `1.0.0`: a commit with the footer `Release-As: 1.0.0-RC1` makes the next
  release pull request propose that version. The release after it goes back to the version the commits give.
- **A version other than the one proposed**: the same `Release-As: x.y.z` footer, in any commit merged before the
  release.

## When something fails

- **The publish job failed** (a missing secret, Maven Central down): the tag and the GitHub release already exist.
  Fix the cause and run the failed job again from the Actions tab. It publishes the same tagged commit.
- **The release pull request does not open**: the repository must allow GitHub Actions to create pull requests
  (*Settings → Actions → General → Workflow permissions*).

The release pull request is opened with the workflow's own token, so CI does not run on it. It only changes the
changelog and the versions, and CI runs on `main` after the merge.

## One-time setup

The Release workflow reads four repository secrets:

| Secret | What it is |
|---|---|
| `MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD` | A user token of the [Central Portal](https://central.sonatype.com) account that owns the `io.github.soleworks` namespace |
| `SIGNING_KEY` | The ASCII-armored private GPG key that signs the artifacts, with its public key on a keyserver |
| `SIGNING_KEY_PASSWORD` | The passphrase of that key |

The history before `0.1.0` is not in the changelog: `bootstrap-sha` in `release-please-config.json` starts it at the
commit that set up this flow.
