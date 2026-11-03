# Docs site conventions

Each rule here is checked: by the harness (`DocsConventionsTest`), by the Mintlify CLI, by Stylelint
(`docs/.stylelintrc.json`) or by Vale (`docs/.vale.ini`). The rule names its check.

## Facts are variables

A fact of the project, such as the version, the Kotlin and Java versions, the address of the site or a
count (constraints, countries of postal codes, countries of documents), is a variable of `docs.json`, and a
page writes `{{version}}`, never `0.1.0`. Mintlify fills variables in when it builds the site, in prose and
in code blocks, so the Markdown agents read has the values too.

- Each variable is checked against the code: `version` against `.release-please-manifest.json`, `kotlin`
  against the version catalog, `java` against the toolchain, the counts against the sources.
- `version` is the last stable version released, never the one being built: `gradle.properties` holds the
  next version (`0.1.0-SNAPSHOT`), which is not on Maven Central, and a release candidate never reaches the
  docs. release-please sets the manifest and the variable in the same release commit. Before the first
  release the manifest is `0.0.0`, and nothing shows the version: no page writes `{{version}}`, the
  dependency snippet names no version, and the navbar has no version link.
- Once a release is on Maven Central, the navbar links to
  `https://github.com/soleworks/validity/releases/latest` with the bare version as its label (`0.1.0`, no
  `v`). Mintlify does not fill variables in `docs.json`, so the label holds the value. The `show-release`
  job of the Release workflow adds the link at the first release, with the dependency blocks in place of
  the note that Validity is not on Maven Central yet (`.github/scripts/show-release.mjs`). From then on
  release-please updates the label through its `jsonpath` in `release-please-config.json`, and the harness
  compares it with `version`.
- Mintlify does not fill variables in a frontmatter, in `skill.md` or in a link the link checker reads: a
  frontmatter says the fact without the number, `skill.md` writes the value (and the harness compares it),
  and a link to the site itself is a path from the root, such as `/quickstart`.

Checked by the harness (Facts).

## Repeated content is a snippet

A block that two pages show, such as the dependency to add or the note that Validity is not on Maven
Central yet, is an `.mdx` snippet in `docs/snippets/`, imported where it is shown. A snippet takes props for
what changes between uses (`<Dependency artifact="validity-phone" />`), and a prop is read as `{artifact}` in
text and in code blocks, or as `<code>{artifact}</code>` for inline code, since backticks do not fill props.

- No sentence of six words or more, and no code block of three lines or more, is written twice.
- Every snippet is imported by a page.
- Content is an `.mdx` snippet, not a `.jsx` component: the Markdown of a page leaves out what a component
  renders, so agents would not see it.

Checked by the harness (Repetition, Snippets).

## Code is a sample

A code block titled with a file name, such as `` ```kotlin Account.kt ``, is the code of the sample of that
name in `samples/` (`samples/docs/` for the docs), without its package and imports, and the violations shown
after it are the ones a test of the entry checks. The sample keeps the code in one place, so the same sample
may be shown on more than one page. Change the sample and its test first, then the block. Checked by the
harness (Samples).

## A constraint section

Each public constraint has a section on the page of its kind in `docs/constraints/`: a `` ### `name` ``
heading, the call and the types it works on, what it checks, and its facts through the snippet:

```mdx
<ConstraintFacts code="minLength" message="must have at least {min} characters" change='minLength = "…"' />
```

`code` is the name of the constraint, `message` its default as the messages class holds it, and `change` the
line that changes it in `messages { }`, with its block for a country or a module (`brazil { cpf = "…" }`). A
constraint with no default message, such as `or`, gives only its code. Checked by the harness (Constraints,
Messages, which also checks that the template of `guides/messages.mdx` gives every message with its default).

## Styles

Every color is a variable of `docs/tokens.css`; `docs/style.css` uses them with `var()`. Lengths are in `rem`
or `em`, except a `1px` border. Nothing is placed with a negative margin or an absolute position: the theme
owns the spacing, so a page that compensates for it breaks when the theme changes. Selectors target only the
hooks Mintlify documents (`#page-title`, `.card`, `.code-block`, `.code-group`, `[data-component-part="…"]`) and the `v-`
classes of these pages. Checked by Stylelint.

A page uses only `v-` classes that the stylesheets define, never an inline `style` or a color, and every `v-`
class of the stylesheets is used. Checked by the harness (Styles).

## Prose

The voice of the site: no filler (`simply`, `just`, `easy`, `please`...), no exclamation marks, headings in
sentence case with names of the API in code, and no misspelled words. A word of the domain that the
dictionary does not know goes in `docs/.vale/styles/config/vocabularies/Validity/accept.txt`; a camelCase word
is a name of the API and is never flagged. Checked by Vale.

Every page has a `title` and a `description` of at most 160 characters, and is in the navigation of
`docs.json`. Checked by the harness (Pages, Navigation).

## Running the checks

- `./gradlew build` runs the docs checks of the harness, with the rest of the build.
- `npm run docs:check` runs `mint validate` (failing on its warnings too), the link check with anchors,
  snippets and redirects, the accessibility check and Stylelint. `npm ci` installs them once.
- `vale .`, in `docs/`, checks the prose.
- `npm run docs:dev` serves the site at `localhost:3000`.

After every edit of a page or a stylesheet, the `check-docs.sh` hook runs these on the file and returns what
they found. CI runs all of them in the `docs` job.
