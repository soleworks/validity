# Public API

## Overview

Everything public in a `validity-*` module is what users compile against, and a version on Maven Central
can never be changed. `api/<module>.api` holds the public API of each module, and the build fails when
the code no longer matches it. The version and the changelog come from the commits; `RELEASING.md` has
the whole flow.

## Concepts

| Concept | Where |
|---|---|
| Changing the public API | [conventions.md](conventions.md#changing-the-public-api) |
| A breaking change | [conventions.md](conventions.md#a-breaking-change) |
| Commits | [conventions.md](conventions.md#commits) |
| Releases | `RELEASING.md` |
