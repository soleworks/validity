plugins {
    id("validity.kotlin")
}

dependencies {
    testImplementation(libs.konsist)
}

// Konsist reads the sources of the library modules, and DocsConventionsTest the pages of the docs site, so both are
// inputs of these tests.
tasks.named<Test>("test") {
    inputs
        .files(fileTree(rootDir) { include("validity-*/src/**/*.kt") })
        .withPropertyName("librarySources")
        .withPathSensitivity(PathSensitivity.RELATIVE)
    inputs
        .files(fileTree(rootDir) { include("docs/**/*.mdx", "docs/docs.json") })
        .withPropertyName("docsPages")
        .withPathSensitivity(PathSensitivity.RELATIVE)
    systemProperty("validity.root", rootDir.absolutePath)
}
