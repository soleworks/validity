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
   since the last release, and the entries of `CHANGELOG.md`: `feat`, `fix`, `perf` and `revert` commits (the defaults of release-please), and every breaking change. It
   also sets the version in `gradle.properties`, which the build publishes.
3. **Merging that pull request is the release.** release-please tags the merge commit (`v0.2.0`) and creates the
   GitHub release with its changelog. The same workflow then checks that the build has that version, builds it, and
   publishes `validity-core` and `validity-phone`, signed, to Maven Central.
4. **The docs and the README follow by themselves.** The `show-release` job waits until
   [.github/scripts/wait-for-maven-central.sh](.github/scripts/wait-for-maven-central.sh) can download the version
   from Maven Central, which takes minutes after the publish, then runs
   [.github/scripts/show-release.mjs](.github/scripts/show-release.mjs). The script is the only writer of the version a
   reader sees: the `version` variable of `docs/docs.json`, which every page of the docs site reads as `{{version}}`,
   the version link of the docs navbar, and the dependencies and the badge of the README. At the first release it also
   puts the dependency blocks in place of the notes that Validity is not on Maven Central yet. The job runs the docs
   checks and the harness, then pushes the change to `main` as `docs: show version 0.2.0`.

The merge is the only step a person takes. A reader is never told to add a version that cannot be downloaded: before
the first release nothing names a version, a release shows up in the docs once Maven Central has it, and a release
candidate never reaches the docs. The harness holds it: the `version` variable is a stable version, at most the one in
`.github/.release-please-manifest.json`, and the navbar label and the README name that same version.

## What CI checks on every pull request

- **Every commit is a Conventional Commit, and so is the title.** A commit release-please cannot read would be left
  out of the version and the changelog, and a squash merge of several commits makes the title the subject of the
  commit it reads. The *Pull request* workflow runs this check again when the title changes.
- **The public API matches its dump.** `api/<module>.api` holds the public API of each published module, and
  `./gradlew check` fails when the code no longer matches it. A pull request that changes the API on purpose runs
  `./gradlew updateKotlinAbi` and commits the new dump, so the review shows the change.
- **A break is declared.** When the dump loses or changes a line, one of the commits needs the `!` and the
  `BREAKING CHANGE:` footer, so the version cannot go up by less than it should.

The script behind the commit, title and break checks is
[.github/scripts/check-commits.sh](.github/scripts/check-commits.sh).

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
- **The `show-release` job failed**: the docs do not show the version yet. When it waited an hour without seeing the
  version on Maven Central, look for the deployment on the Central Portal, which may have rejected it. Run the job
  again once the version is there: the script changes only what is missing.
- **A release candidate failed**: the workflow comments on the release pull request with the link to the run. Run it
  again once the cause is fixed. A failure before the upload keeps the number; once a candidate is uploaded its tag
  exists, and the next run takes the next number, since Maven Central never takes the same version twice.
- **The release pull request does not open, or the docs are not pushed**: the `validity-release` GitHub App must be
  installed on the repository, and its client ID and private key set as below. A new private key replaces a lost one.

## One-time setup

The Release workflows read four repository secrets for Maven Central:

| Secret | What it is |
|---|---|
| `MAVEN_CENTRAL_USERNAME`, `MAVEN_CENTRAL_PASSWORD` | A user token of the [Central Portal](https://central.sonatype.com) account that owns the `io.github.soleworks` namespace |
| `SIGNING_KEY` | The ASCII-armored private GPG key that signs the artifacts, with its public key on a keyserver |
| `SIGNING_KEY_PASSWORD` | The passphrase of that key |

release-please and the `show-release` job act as the `validity-release` GitHub App of the `soleworks` organization,
installed on this repository only, with read and write access to contents, pull requests, issues and commit statuses.
A pull request that the workflow's own token opens runs no workflow, so without the App CI would not run on the release
pull request, and `main` would not accept it. The App is set by the repository variable `RELEASE_APP_CLIENT_ID` and the
secret `RELEASE_APP_PRIVATE_KEY`.

`main` has a ruleset: changes come through pull requests, with the `build`, `docs` and `commits` checks passing, and no
force push or deletion. The App is on its bypass list, since `show-release` pushes the docs straight to `main`.

Pull requests are merged by squash only, with the title of the pull request as the subject of the commit and the
messages of its commits as the body. release-please reads that subject, and a `BREAKING CHANGE:` footer of any commit
reaches it through the body. A merge commit would repeat the message of a commit it merges, and the changelog would
list the change twice.

The history before `0.1.0` is not in the changelog: `bootstrap-sha` in `.github/release-please-config.json` starts it at the
commit that set up this flow.
