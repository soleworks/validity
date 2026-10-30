#!/usr/bin/env bash
# Checks the commits of a pull request, since release-please reads the next version and the changelog from them.
#
# 1. Every commit is a Conventional Commit, so none is left out of the changelog.
# 2. When the public API loses or changes a declaration (a removed line in api/*.api), a commit says so with a `!`
#    and a BREAKING CHANGE: footer, so the version goes up as it should.
#
# Usage: check-commits.sh <base> <head>
set -euo pipefail

base=$1
head=$2
types='feat|fix|perf|refactor|revert|docs|test|build|ci|chore|style'
failed=0

for sha in $(git rev-list --no-merges "$base..$head"); do
    subject=$(git log -1 --format=%s "$sha")

    if ! grep -Eq "^($types)(\([a-z0-9-]+\))?!?: .+" <<< "$subject"; then
        echo "::error::${sha:0:7} \"$subject\" is not a Conventional Commit, such as \"feat(core): ...\" or \"fix: ...\"."
        failed=1
    fi
done

if git diff "$base...$head" -- '*.api' | grep -Eq '^-[^-]'; then
    messages=$(git log --no-merges --format=%B "$base..$head")

    if ! grep -Eq "^($types)(\([a-z0-9-]+\))?!: " <<< "$messages" || ! grep -q '^BREAKING CHANGE: ' <<< "$messages"; then
        echo "::error::The public API lost or changed a declaration (see the diff of api/*.api). Say what breaks in a commit with a ! and a BREAKING CHANGE: footer, such as \"feat(core)!: ...\"."
        failed=1
    fi
fi

exit $failed
