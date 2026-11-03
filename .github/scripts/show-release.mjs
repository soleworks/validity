// Shows a version in the docs site and the README once it is on Maven Central. The Release workflow runs it after the
// publish job, when wait-for-maven-central.sh has seen the version there, so a reader is never told to add a version
// that cannot be downloaded yet. It is the only writer of the version a reader sees: the `version` variable and the
// navbar link of docs/docs.json, and the dependencies and the badge of the README. release-please writes the version
// only in gradle.properties, which the build needs before it publishes. At the first release, it also puts the
// dependency blocks in place of the notes that Validity is not on Maven Central yet. It changes nothing when the docs
// and the README already show the version, so running it again is safe.
// Usage: node .github/scripts/show-release.mjs 0.1.0
import { existsSync, readdirSync, readFileSync, rmSync, writeFileSync } from "node:fs";
import { join } from "node:path";

const RELEASES = "https://github.com/soleworks/validity/releases/latest";
const NOTE = "docs/snippets/maven-central-note.mdx";
const NOTE_IMPORT = 'import MavenCentralNote from "/snippets/maven-central-note.mdx";\n';
const NOTE_USE = /\n<MavenCentralNote \/>\n/g;
const README_NOTE = /> \[!NOTE]\n> Validity is not on Maven Central yet:.*?\n\n/s;
const README_DEPENDENCY = /(io\.github\.soleworks:validity-[a-z]+)(:[^"]*)?"/g;
const README_BADGE = /\[!\[Maven Central]\([^)]*\)]\([^)]*\)/;
const DEPENDENCY = `<CodeGroup>

\`\`\`kotlin build.gradle.kts
implementation("io.github.soleworks:{artifact}:{{version}}")
\`\`\`

\`\`\`groovy build.gradle
implementation 'io.github.soleworks:{artifact}:{{version}}'
\`\`\`

\`\`\`xml pom.xml
<dependency>
    <groupId>io.github.soleworks</groupId>
    <artifactId>{artifact}</artifactId>
    <version>{{version}}</version>
</dependency>
\`\`\`

</CodeGroup>
`;

const version = process.argv[2] ?? "";
if (!/^\d+\.\d+\.\d+$/.test(version)) {
  console.error(`"${version}" is not a stable version such as 0.1.0: the docs show only stable versions.`);
  process.exit(1);
}

showInDocs();
showInReadme();

function showInDocs() {
  const path = "docs/docs.json";
  const docs = JSON.parse(readFileSync(path, "utf8"));
  const link = docs.navbar.links.find((candidate) => candidate.href === RELEASES);

  docs.variables.version = version;
  if (link) link.label = version;
  else docs.navbar.links.unshift({ label: version, icon: "tag", href: RELEASES });
  writeFileSync(path, `${JSON.stringify(docs, null, 2)}\n`);

  if (!existsSync(NOTE)) return;
  rmSync(NOTE);
  for (const page of pages("docs")) {
    const content = readFileSync(page, "utf8");
    const shown = content.replace(NOTE_IMPORT, "").replace(NOTE_USE, "");
    if (shown !== content) writeFileSync(page, shown);
  }
  writeFileSync("docs/snippets/dependency.mdx", DEPENDENCY);
}

function showInReadme() {
  const badge =
    `[![Maven Central](https://img.shields.io/static/v1?label=Maven%20Central&message=${version}&color=2F81F7` +
    "&style=flat-square&logo=apachemaven&logoColor=white)]" +
    "(https://central.sonatype.com/artifact/io.github.soleworks/validity-core)";
  const readme = readFileSync("README.md", "utf8")
    .replace(README_NOTE, "")
    .replace(README_DEPENDENCY, `$1:${version}"`)
    .replace(README_BADGE, badge);
  writeFileSync("README.md", readme);
}

function pages(directory) {
  return readdirSync(directory, { withFileTypes: true }).flatMap((entry) => {
    const path = join(directory, entry.name);
    if (entry.isDirectory()) return entry.name === "node_modules" || entry.name.startsWith(".") ? [] : pages(path);
    return entry.name.endsWith(".mdx") ? [path] : [];
  });
}
