plugins {
    id("validity.library")
}

dependencies {
    api(project(":validity-core"))
    implementation(libs.libphonenumber)
}
