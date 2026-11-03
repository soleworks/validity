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
   [release-please](https://github.com/googleapis/release-please) rebuilds it on top of `main` (`always-update`, so it
   never lags behind a commit that leaves the changelog as it was) with the next version, worked out from the commits
   since the last release, and the entries of `CHANGELOG.md`: `feat` and `fix` commits, and every breaking change. It
   also sets the version in `gradle.properties`, in the installation snippet of the README, in the `version` variable
   of `docs/docs.json`, which every page of the docs site reads as `{{version}}`, and in the label of the version link
   of the docs navbar. The docs show only a released version: the harness checks that the `version` variable and the
   navbar label match `.release-please-manifest.json`, not `gradle.properties`.
3. **Merging that pull request is the release.** release-please tags the merge commit (`v0.2.0`) and creates the
   GitHub release with its changelog. The same workflow then checks that the build has that version, builds it, and
   publishes `validity-core` and `validity-phone`, signed, to Maven Central.
4. **The docs follow by themselves.** Once the version is on Maven Central, the `show-release` job runs
   [.github/scripts/show-release.mjs](.github/scripts/show-release.mjs), which adds what release-please cannot write,
   since it only replaces a version already written: the version link of the docs navbar and, at the first release,
   the dependency blocks of the docs and the README in place of the notes that Validity is not on Maven Central yet.
   It runs the docs checks and pushes to `main`. When there is nothing to add, as from the second release on, it
   pushes nothing.

The merge is the only step a person takes. The docs show only a stable version that is on Maven Central: before the
first release nothing shows a version, and a release candidate never reaches the docs.

## What CI checks on every pull request

- **Every commit is a Conventional Commit.** A commit release-please cannot read would be left out of the changelog.
- **The public API matches its dump.** `api/<module>.api` holds the public API of each published module, and
  `./gradlew check` fails when the code no longer matches it. A pull request that changes the API on purpose runs
  `./gradlew updateKotlinAbi` and commits the new dump, so the review shows the change.
- **A break is declared.** When the dump loses or changes a line, one of the commits needs the `!` and the
  `BREAKING CHANGE:` footer, so the version cannot go up by less than it should.

The script behind the last two commit checks is [.github/scripts/check-commits.sh](.github/scripts/check-commits.sh).

## Other versions

- **A release candidate**, which a new major version (`1.0.0`, `2.0.0`) goes through first, and any other version may.
  In the Actions tab, *Release candidate* → *Run workflow* publishes the next candidate of the version the release pull
  request proposes (`1.0.0-RC1`, then `1.0.0-RC2`) to Maven Central, built from the commit of `main` the pull request
  was cut from, so the candidate holds exactly the code that merging the pull request releases. It creates the
  candidate as a GitHub prerelease and comments on the pull request. After fixes from the feedback are merged into
  `main`, running it again publishes the next candidate; with no change since the last one, it publishes nothing and
  says so. A candidate commits nothing: the docs, the changelog and `releases/latest` stay on the last stable version,
  and merging the release pull request releases the stable version itself. While the pull request proposes a major
  version with no candidate published, the Release workflow sets a red *Release candidate* status on it.
- **A version other than the one proposed**: a commit with the footer `Release-As: x.y.z`, merged before the release.

## When something fails

- **The publish job failed** (a missing secret, Maven Central down): the tag and the GitHub release already exist.
  Fix the cause and run the failed job again from the Actions tab. It publishes the same tagged commit, and the
  `show-release` job runs after it.
- **The `show-release` job failed**: the version is on Maven Central and the docs do not show it yet. Run the job again;
  the script changes only what is missing.
- **A release candidate failed**: the workflow comments on the release pull request with the link to the run. Run it
  again once the cause is fixed: the number of the candidate goes up only when one is published.
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
