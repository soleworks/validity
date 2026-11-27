plugins {
    id("validity.library")
}

description = "Validates the request bodies of a Spring Boot application with Validity, and answers every broken rule."

// The module compiles against the lowest Spring Boot it supports. CI also runs its tests on the latest Spring Boot:
// ./gradlew :validity-spring:test -PspringBootTestVersion=latest.release
val springBootTestVersion: String? = providers.gradleProperty("springBootTestVersion").orNull

dependencies {
    api(project(":validity-core"))
    api(libs.spring.web)
    implementation(libs.spring.boot.autoconfigure)
    implementation(libs.kotlinx.coroutines.reactor) {
        // WebFlux brings Reactor, and only a WebFlux application runs the code that needs it.
        exclude(group = "io.projectreactor")
    }
    compileOnly(libs.spring.webmvc)
    compileOnly(libs.spring.webflux)

    testImplementation(platform(libs.spring.boot.dependencies))
    testImplementation(libs.spring.boot.starter.web)
    testImplementation(libs.spring.boot.starter.webflux)
    testImplementation(libs.kotlinx.serialization.json)
    // Spring Boot 3 reads JSON with Jackson 2, and Spring Boot 4 with Jackson 3.
    testRuntimeOnly(
        if (springBootTestVersion?.startsWith("3.") !=
            false
        )
            libs.jackson2.module.kotlin
        else
            libs.jackson3.module.kotlin
    )
}

if (springBootTestVersion != null) {
    configurations.named("testRuntimeClasspath") {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.springframework.boot") useVersion(springBootTestVersion)
        }
    }
}
