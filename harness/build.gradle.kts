plugins {
    id("validity.kotlin")
}

dependencies {
    testImplementation(libs.konsist)
}

// Konsist reads the sources of the library modules, so they are inputs of these tests.
tasks.named<Test>("test") {
    inputs.files(fileTree(rootDir) { include("validity-*/src/**/*.kt") })
        .withPropertyName("librarySources")
        .withPathSensitivity(PathSensitivity.RELATIVE)
}
