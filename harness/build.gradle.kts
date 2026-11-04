plugins {
    id("validity.kotlin")
}

dependencies {
    implementation(libs.konsist)
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

// Writes what the KDoc of each constraint says into its section and its table row in docs/constraints/. The harness
// fails when a page says something else.
tasks.register<JavaExec>("updateConstraintDocs") {
    group = "documentation"
    description = "Writes the KDoc of the constraints into the pages of docs/constraints."
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "io.github.soleworks.validity.harness.UpdateConstraintDocsKt"
    workingDir = rootDir
    systemProperty("validity.root", rootDir.absolutePath)
}
