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
- `version` is the last stable version on Maven Central. Never change it by hand:
  `.github/scripts/show-release.mjs` writes it after a release (see `RELEASING.md`). Before the first release it is `0.0.0`, and nothing shows a version: no page writes
  `{{version}}`, the dependency snippet names no version, and the navbar has no version link.
- From the first release on, the navbar links to `https://github.com/soleworks/validity/releases/latest` with
  the bare version as its label (`0.1.0`, no `v`). Mintlify does not fill variables in `docs.json`, so the
  label holds the value: the same script writes it, and the harness compares it with `version`, as it does
  the dependencies and the badge of the README.
- Mintlify does not fill variables in a frontmatter, in `skill.md` or in a link the link checker reads: a
  frontmatter says the fact without the number, `skill.md` writes the value (and the harness compares it),
  and a link to the site itself is a path from the root, such as `/quickstart`.

Checked by the harness (Facts).

## Repeated content is a snippet

A block that two pages show, such as the dependency to add, is an `.mdx` snippet in `docs/snippets/`, imported where it is shown. A snippet takes props for
what changes between uses (`<Dependency artifact="validity-phone" />`), and a prop is read as `{artifact}` in
text and in code blocks, or as `<code>{artifact}</code>` for inline code, since backticks do not fill props.

- No sentence of six words or more, and no code block of three lines or more, is written twice.
- Every snippet is imported by a page.
- Content is an `.mdx` snippet, not a `.jsx` component: the Markdown of a page leaves out what a component
  renders, so agents would not see it. No check holds this one.

The first two are checked by the harness (Repetition, Snippets).

## Code is a sample

A code block titled with a file name, such as `` ```kotlin Account.kt ``, is the code of the sample of that
name in `samples/` (`samples/docs/` for the docs), without its package and imports. The sample keeps the code
in one place, so the same sample may be shown on more than one page. Change the sample and its test first,
then the block. Checked by the harness (Samples). The violations shown after a block are copied by hand from
the sample's test, which no check compares, so change them together.

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

What it checks comes from its KDoc, never written by hand: the paragraph right after the call is the first
paragraph of the KDoc, and its row in the table at the top of the page is that sentence without "Checks that
the <value>". `./gradlew updateConstraintDocs` writes both (`harness/src/main/.../ConstraintPages.kt`), the hook
that formats a `*Constraints.kt` file runs it, and the harness fails on a page that says something else
(Constraints). To change that sentence, change the KDoc. A paragraph after it, such as a note on the arguments,
is written by hand.

## Styles

Every color is a variable of `docs/tokens.css`; `docs/style.css` uses them with `var()`. Lengths are in `rem`
or `em`, except a `1px` border. Nothing is placed with a negative margin or an absolute position: the theme
owns the spacing, so a page that compensates for it breaks when the theme changes. Selectors target only the
hooks Mintlify documents (`#page-title`, `.card`, `.code-block`, `.code-group`, `[data-component-part="…"]`)
and the `v-` classes of these pages. Checked by Stylelint.

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

After every edit of a page or a stylesheet, the `check-docs.sh` hook runs Vale, `mint validate`, Stylelint and
the docs checks of the harness on it, and returns what they found. CI runs `npm run docs:check` and Vale in the
`docs` job, and the harness in the `build` job.
