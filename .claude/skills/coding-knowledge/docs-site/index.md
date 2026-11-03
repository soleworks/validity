# Docs site

## Overview

The documentation site lives in `docs/`, built by Mintlify from `docs.json` and the `.mdx` pages, and
published on every push to `main`. Its readers are people and agents: every page is also served as
Markdown, so what a page says must read well without the browser.

Each fact has one home, and the pages point at it instead of copying it: a fact of the project is a
variable of `docs.json`, a block shown on several pages is a snippet, code is a sample, and the facts of a
constraint come from its code. The checks below fail a page that writes a fact a second time.

## Concepts

| Concept | Where |
|---|---|
| Facts are variables | [conventions.md](conventions.md#facts-are-variables) |
| Repeated content is a snippet | [conventions.md](conventions.md#repeated-content-is-a-snippet) |
| Code is a sample | [conventions.md](conventions.md#code-is-a-sample) |
| A constraint section | [conventions.md](conventions.md#a-constraint-section) |
| Styles | [conventions.md](conventions.md#styles) |
| Prose | [conventions.md](conventions.md#prose) |
| Running the checks | [conventions.md](conventions.md#running-the-checks) |
