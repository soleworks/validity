# Contributing to Validity

`./gradlew build` compiles, tests and checks every module. Three tools keep the code in one style:

- **ktlint** formats every Kotlin file with the rules in [.editorconfig](.editorconfig). `./gradlew ktlintFormat` fixes what it can and lists the rest.
- **Detekt** runs every rule it has, the libraries rule set included. [config/detekt/detekt.yml](config/detekt/detekt.yml) lists the few rules this project tunes, each with its reason.
- **Konsist**, in the internal `harness` module, checks the conventions Detekt cannot see: how a constraint is declared, how values are named, what a production file may import, and that every test is a Given, a When and a Then, ordered by what it checks.

**Kover** measures what the tests cover in `validity-core` and `validity-phone`, and the build fails below 99% of the lines or 93% of the branches. `./gradlew :koverHtmlReport` writes the report to `build/reports/kover/html`, and CI uploads it to [Codecov](https://codecov.io/gh/soleworks/validity), which asks every line a pull request changes to be covered ([codecov.yml](codecov.yml)).

None of these tools, nor Kotest, is a dependency of a published module.

The examples in the README are samples in
[validity-core/src/test/.../samples/readme](validity-core/src/test/kotlin/io/github/soleworks/validity/samples/readme),
and a test checks the output shown next to them. When you change an example, change its sample too.

The docs site lives in [docs](docs), built by [Mintlify](https://www.mintlify.com) from `docs.json` and its `.mdx`
pages, and published on every push to `main`. After `npm ci`, `npm run docs:dev` serves it at `localhost:3000`. Each fact
has one home: the version and the counts are variables of `docs.json`, a block shown twice is a snippet, and a code block
titled with a file name, such as `Account.kt`, is a sample in
[samples/docs](validity-core/src/test/kotlin/io/github/soleworks/validity/samples/docs). Four tools hold that:

- **The harness** checks the samples, the facts against the code, the facts of each constraint against its messages,
  and that no sentence or code block is written twice.
- **The Mintlify CLI** checks the MDX, the navigation, the links and their anchors, and accessibility.
- **Stylelint** keeps colors in `tokens.css` and the layout free of pixel offsets and of Mintlify's internal classes.
- **[Vale](https://vale.sh)** checks spelling and the voice of the prose.

`./gradlew build` runs the harness, `npm run docs:check` the Mintlify CLI and Stylelint, and `vale .` in `docs` the prose.

The public API of each published module is kept in `api/<module>.api`, and `./gradlew check` fails when it changes
without `./gradlew updateKotlinAbi`. [RELEASING.md](RELEASING.md) describes how a version is born and published.
