// Runs `mint validate` in the current folder, which npm sets to docs/, and fails on its warnings too. Mintlify
// reports some mistakes, such as a component that does not exist or an H1 that repeats the title, as warnings that
// do not fail the run.
// Usage, from docs/: node ../.github/scripts/mint-validate.mjs [mint validate flags, such as --files
// guides/fail-fast.mdx --no-build]
import { spawnSync } from "node:child_process";

const run = spawnSync("npx", ["--no-install", "mint", "validate", "--format", "json", ...process.argv.slice(2)], {
  encoding: "utf8",
  shell: process.platform === "win32",
});

let report;
try {
  report = JSON.parse(run.stdout);
} catch {
  console.error(run.stderr || run.stdout || "mint validate printed no report.");
  process.exit(1);
}

for (const finding of report.diagnostics) {
  const place = [finding.file, finding.line, finding.column].filter((part) => part !== undefined).join(":");
  console.error(`${place} ${finding.severity} ${finding.rule}: ${finding.message}`);
}
if (report.build?.ran && !report.build.passed) console.error("The strict build of the site failed.");

const failed = report.summary.errors > 0 || report.summary.warnings > 0 || (report.build?.ran && !report.build.passed);
process.exit(failed ? 1 : 0);
