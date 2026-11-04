// Renders the image a shared link to Validity shows, from .github/social-preview/index.html, with a headless Chrome
// or Edge: docs/images/social-preview.png at the 1200x630 of Open Graph for the docs site, and
// .github/social-preview/github.png at the 1280x640 GitHub asks for the Social preview of the repository, which is
// uploaded by hand in Settings > General. Run it after changing the page.
// Usage: node .github/scripts/social-preview.mjs (CHROME=<path> picks the browser)
import { execFileSync } from "node:child_process";
import { existsSync } from "node:fs";
import { resolve } from "node:path";
import { pathToFileURL } from "node:url";

const PAGE = pathToFileURL(resolve(".github/social-preview/index.html")).href;
const IMAGES = [
  { file: "docs/images/social-preview.png", width: 1200, height: 630 },
  { file: ".github/social-preview/github.png", width: 1280, height: 640 },
];
const BROWSERS = [
  process.env.CHROME,
  "C:/Program Files/Google/Chrome/Application/chrome.exe",
  "C:/Program Files (x86)/Microsoft/Edge/Application/msedge.exe",
  "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome",
  "/usr/bin/google-chrome",
  "/usr/bin/chromium",
];

const browser = BROWSERS.find((path) => path && existsSync(path));
if (!browser) throw new Error("No Chrome or Edge found: set CHROME to its path.");

for (const { file, width, height } of IMAGES) {
  execFileSync(browser, [
    "--headless=new",
    "--hide-scrollbars",
    "--force-device-scale-factor=1",
    "--virtual-time-budget=10000",
    `--window-size=${width},${height}`,
    `--screenshot=${resolve(file)}`,
    PAGE,
  ], { stdio: "inherit" });
  console.log(`${file} (${width}x${height})`);
}
