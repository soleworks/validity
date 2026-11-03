#!/bin/bash
# After a Kotlin file is written, ktlint formats it with the rules in .editorconfig. What ktlint cannot fix
# goes back to the agent, which fixes it by hand.
FILE=$(python -c 'import json, sys; print(json.load(sys.stdin).get("tool_input", {}).get("file_path", ""))')
[[ ("$FILE" == *.kt || "$FILE" == *.kts) && -f "$FILE" ]] || exit 0
cd "$CLAUDE_PROJECT_DIR" || exit 0

# The ktlint of gradle/libs.versions.toml, resolved by Gradle once and again after the catalog changes.
CLASSPATH=build/ktlint/classpath.txt
if [[ ! "$CLASSPATH" -nt gradle/libs.versions.toml ]]; then
    ./gradlew -q ktlintClasspath >&2 || { echo "Gradle could not resolve ktlint; the file was not formatted." >&2; exit 1; }
fi

OUT=$(java --add-opens=java.base/java.lang=ALL-UNNAMED -cp "$(cat "$CLASSPATH")" \
    com.pinterest.ktlint.Main -F "$FILE" 2>&1) && exit 0
echo "ktlint could not fix these in $FILE; fix them by hand following .editorconfig:" >&2
FOUND=$(echo "$OUT" | grep "(standard:" | sort -u)
echo "${FOUND:-$OUT}" >&2
exit 2
