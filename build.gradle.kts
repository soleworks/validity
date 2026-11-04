import kotlinx.kover.gradle.plugin.dsl.CoverageUnit

plugins {
    base
    id("org.jetbrains.kotlinx.kover")
}

repositories {
    mavenCentral()
}

// ktlint formats every Kotlin file of the repo with the rules in .editorconfig. ktlintCheck runs in check;
// ./gradlew ktlintFormat fixes what it can and lists the rest. The format hook of .claude runs the same ktlint.
val ktlint: Configuration by configurations.creating

dependencies {
    // One coverage report for the published modules, in build/reports/kover: ./gradlew :koverXmlReport
    kover(project(":validity-core"))
    kover(project(":validity-phone"))

    ktlint(libs.ktlint.cli) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val kotlinFiles = listOf(
    "**/src/**/*.kt",
    "**/*.kts",
    "!**/build/**",
    "!.claude/**"
)

val ktlintCheck by tasks.registering(JavaExec::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Checks the Kotlin code style with ktlint."
    classpath = ktlint
    mainClass = "com.pinterest.ktlint.Main"
    args(kotlinFiles)
}

tasks.register<JavaExec>("ktlintFormat") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Formats the Kotlin code with ktlint and lists what it could not fix."
    classpath = ktlint
    mainClass = "com.pinterest.ktlint.Main"
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    args(listOf("-F") + kotlinFiles)
}

// The format hook runs ktlint on one file without Gradle: this writes the classpath it runs from.
tasks.register("ktlintClasspath") {
    val classpathFile = layout.buildDirectory.file("ktlint/classpath.txt")
    val ktlintJars = files(ktlint)
    inputs.files(ktlintJars)
    outputs.file(classpathFile)
    doLast {
        classpathFile.get().asFile.writeText(ktlintJars.asPath)
    }
}

// The build fails when coverage drops below these floors, set just under what the tests reach today.
// Codecov reads the same report in CI (.github/codecov.yml).
kover {
    reports {
        verify {
            rule {
                minBound(99, CoverageUnit.LINE)
                minBound(93, CoverageUnit.BRANCH)
            }
        }
    }
}

tasks.named("check") {
    dependsOn(ktlintCheck, "koverVerify")
}
