package io.github.soleworks.validity.harness

/** Writes the pages of the constraints from the KDoc of the constraints: `./gradlew updateConstraintDocs`. */
fun main() {
    constraintPageFiles.forEach { file -> file.writeText(constraintPage(file)) }
}
