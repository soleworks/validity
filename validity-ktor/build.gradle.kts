plugins {
    id("validity.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

description = "Validates the request bodies of a Ktor server with Validity, and answers every broken rule."

dependencies {
    api(project(":validity-core"))
    api(libs.ktor.server.core)
    implementation(libs.kotlinx.serialization.core)

    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.ktor.server.content.negotiation)
    testImplementation(libs.ktor.serialization.kotlinx.json)
    testImplementation(libs.ktor.serialization.jackson)
}

// The module compiles against the lowest Ktor it supports. CI also runs its tests on the latest Ktor:
// ./gradlew :validity-ktor:test -PktorTestVersion=latest.release
providers.gradleProperty("ktorTestVersion").orNull?.let { ktorTestVersion ->
    configurations.named("testRuntimeClasspath") {
        resolutionStrategy.eachDependency {
            if (requested.group == "io.ktor") useVersion(ktorTestVersion)
        }
    }
}
