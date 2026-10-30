plugins {
    id("validity.library")
}

description = "Validation for Kotlin, written in plain Kotlin."

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}
