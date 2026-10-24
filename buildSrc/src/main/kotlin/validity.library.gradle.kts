plugins {
    id("validity.kotlin")
    `java-library`
    id("org.jetbrains.kotlinx.kover")
}

kotlin {
    explicitApi()
}
