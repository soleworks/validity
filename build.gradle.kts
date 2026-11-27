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
    kover(project(":validity-ktor"))

    ktlint(libs.ktlint.cli) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

// Gradle picks the files, with patterns relative to this checkout. ktlint's own globs can't skip .claude/ in a
// worktree, which lives under .claude/worktrees/: on Windows ktlint prefixes every glob with **/, so !.claude/**
// matched every file of the worktree and ktlint checked none.
val kotlinFiles = fileTree(rootDir) {
    include("**/src/**/*.kt", "**/*.kts")
    exclude("**/build/**", ".claude/**")
}

// Runs ktlint with these options on kotlinFiles, which it reads from stdin. --info lists the files.
fun JavaExec.runKtlint(
    vararg options: String
) {
    classpath = ktlint
    mainClass = "com.pinterest.ktlint.Main"
    args(*options, "--patterns-from-stdin")
    val files = kotlinFiles
    val root = rootDir
    doFirst {
        val paths = files.map { it.relativeTo(root).invariantSeparatorsPath }.sorted()
        check(paths.isNotEmpty()) { "No Kotlin files found under $root." }
        logger.info("ktlint checks ${paths.size} files:\n${paths.joinToString("\n")}")
        (this as JavaExec).standardInput = paths.joinToString("\u0000").byteInputStream()
    }
}

val ktlintCheck by tasks.registering(JavaExec::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Checks the Kotlin code style with ktlint."
    runKtlint()
}

tasks.register<JavaExec>("ktlintFormat") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Formats the Kotlin code with ktlint and lists what it could not fix."
    jvmArgs("--add-opens=java.base/java.lang=ALL-UNNAMED")
    runKtlint("-F")
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
// Codecov reads the same report in CI (codecov.yml).
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
