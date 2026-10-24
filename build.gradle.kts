plugins {
    id("org.jetbrains.kotlinx.kover")
}

repositories {
    mavenCentral()
}

// One coverage report for the published modules, in build/reports/kover: ./gradlew :koverXmlReport
dependencies {
    kover(project(":validity-core"))
    kover(project(":validity-phone"))
}
