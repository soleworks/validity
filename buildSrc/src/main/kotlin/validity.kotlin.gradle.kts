plugins {
    id("org.jetbrains.kotlin.jvm")
    id("dev.detekt")
}

private val libs = versionCatalogs.named("libs")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.findLibrary("junit-jupiter").get())
    testImplementation(libs.findLibrary("kotest-assertions-core").get())
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    detektPlugins(libs.findLibrary("detekt-rules-ktlint-wrapper").get())
    detektPlugins(libs.findLibrary("detekt-rules-libraries").get())
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = true
    config.setFrom(rootProject.layout.projectDirectory.file("config/detekt/detekt.yml"))
    autoCorrect = providers.gradleProperty("autoCorrect").map(String::toBoolean).orElse(false)
}

// detektMain and detektTest run every rule with type resolution, which knows explicit API mode.
// The plain detekt task does not, and reports every `public` as redundant.
tasks.named("detekt") {
    enabled = false
}

tasks.named("check") {
    dependsOn("detektMain", "detektTest")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
