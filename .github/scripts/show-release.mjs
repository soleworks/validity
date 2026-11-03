// Shows a version in the docs site and the README once it is on Maven Central. The Release workflow runs it after the
// publish job. release-please writes the version itself in the release commit, but it only replaces a version that is
// already written, so this script adds what it cannot create: the version link of the docs navbar and, at the first
// release, the dependency blocks that name the version, in place of the notes that Validity is not on Maven Central
// yet. It changes nothing when the docs and the README already show the version, so running it again is safe.
// Usage: node .github/scripts/show-release.mjs 0.1.0
import { existsSync, readdirSync, readFileSync, rmSync, writeFileSync } from "node:fs";
import { join } from "node:path";

const RELEASES = "https://github.com/soleworks/validity/releases/latest";
const NOTE = "docs/snippets/maven-central-note.mdx";
const NOTE_IMPORT = 'import MavenCentralNote from "/snippets/maven-central-note.mdx";\n';
const NOTE_USE = /\n<MavenCentralNote \/>\n/g;
const README_NOTE = /> \[!NOTE]\n> Validity is not on Maven Central yet:.*?\n\n/s;
const README_BADGE = /\[!\[Maven Central]\(https:\/\/img\.shields\.io\/badge\/Maven%20Central-coming%20soon-[^)]*\)]\(#installation\)/;
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

showInDocsJson();
if (existsSync(NOTE)) showFirstRelease();

function showInDocsJson() {
  const path = "docs/docs.json";
  const docs = JSON.parse(readFileSync(path, "utf8"));
  const link = docs.navbar.links.find((candidate) => candidate.href === RELEASES);

  docs.variables.version = version;
  if (link) link.label = version;
  else docs.navbar.links.unshift({ label: version, icon: "tag", href: RELEASES });
  writeFileSync(path, `${JSON.stringify(docs, null, 2)}\n`);
}

function showFirstRelease() {
  rmSync(NOTE);
  for (const page of pages("docs")) {
    const content = readFileSync(page, "utf8");
    const shown = content.replace(NOTE_IMPORT, "").replace(NOTE_USE, "");
    if (shown !== content) writeFileSync(page, shown);
  }
  writeFileSync("docs/snippets/dependency.mdx", DEPENDENCY);

  const readme = readFileSync("README.md", "utf8");
  const badge =
    `[![Maven Central](https://img.shields.io/badge/Maven%20Central-${version}-2F81F7?style=flat-square&logo=apachemaven` +
    "&logoColor=white)](https://central.sonatype.com/artifact/io.github.soleworks/validity-core) " +
    "<!-- x-release-please-version -->";
  writeFileSync("README.md", readme.replace(README_NOTE, "").replace(README_BADGE, badge));
}

function pages(directory) {
  return readdirSync(directory, { withFileTypes: true }).flatMap((entry) => {
    const path = join(directory, entry.name);
    if (entry.isDirectory()) return entry.name === "node_modules" || entry.name.startsWith(".") ? [] : pages(path);
    return entry.name.endsWith(".mdx") ? [path] : [];
  });
}
