#!/bin/bash
# After a file of the docs site is written, the checks of the docs run on it: Vale and the Mintlify CLI for a page,
# Stylelint for a stylesheet, then the docs checks of the harness. What they report goes back to the agent, which
# fixes it before going on.
FILE=$(python -c 'import json, sys; print(json.load(sys.stdin).get("tool_input", {}).get("file_path", ""))')
[[ -f "$FILE" ]] || exit 0
cd "$CLAUDE_PROJECT_DIR" || exit 0
RELATIVE=$(python -c 'import os, sys; print(os.path.relpath(sys.argv[1]).replace(os.sep, "/"))' "$FILE")
[[ "$RELATIVE" == docs/* ]] || exit 0
[[ "$RELATIVE" == *.mdx || "$RELATIVE" == *.md || "$RELATIVE" == *.css || "$RELATIVE" == docs/docs.json ]] || exit 0

[[ -d node_modules ]] || npm ci --no-audit --no-fund >&2 || { echo "npm ci failed; the docs were not checked." >&2; exit 1; }
# Vale from the PATH, or where winget installs it on Windows for a shell started before the install.
VALE=$(command -v vale || ls "$(cygpath -u "${LOCALAPPDATA:-}" 2>/dev/null)"/Microsoft/WinGet/Packages/errata-ai.Vale_*/vale.exe 2>/dev/null | head -1)
FAILED=0
report() {
    echo "$1" >&2
    echo "$2" >&2
    FAILED=1
}

case "$RELATIVE" in
    *.mdx | *.md)
        PAGE=${RELATIVE#docs/}
        if [[ -n "$VALE" ]]; then
            OUT=$(cd docs && "$VALE" --output=line "$PAGE" 2>&1) || report "Vale found these in $RELATIVE:" "$OUT"
        else
            echo "Vale is not installed, so the prose of $RELATIVE was not checked." >&2
        fi
        if [[ "$RELATIVE" == *.mdx ]]; then
            OUT=$(node .github/scripts/mint-validate.mjs --files "$PAGE" --no-build 2>&1) ||
                report "mint validate found these in $RELATIVE:" "$OUT"
        fi
        ;;
    *.css)
        OUT=$(npx --no-install stylelint "$RELATIVE" 2>&1) || report "Stylelint found these in $RELATIVE:" "$OUT"
        ;;
esac

if ! ./gradlew -q :harness:test --tests "io.github.soleworks.validity.harness.DocsConventionsTest" >/dev/null 2>&1; then
    FAILURES=$(python - <<'EOF'
import glob, html, re
for report in glob.glob("harness/build/test-results/test/*DocsConventionsTest*.xml"):
    text = open(report, encoding="utf-8").read()
    for case in re.finditer(r'<testcase name="([^"]+)"[^>]*>\s*<failure message="([^"]*)"', text):
        print("- " + case.group(1).removesuffix("()") + ": " + html.unescape(case.group(2)))
EOF
)
    report "The docs checks of the harness fail; fix what they list:" "${FAILURES:-run ./gradlew :harness:test to see why}"
fi

[[ "$FAILED" == 0 ]] || exit 2
