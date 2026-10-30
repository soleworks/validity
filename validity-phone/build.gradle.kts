plugins {
    id("validity.library")
}

description = "Phone number constraints for Validity, with Google's libphonenumber."

dependencies {
    api(project(":validity-core"))
    implementation(libs.libphonenumber)
}
