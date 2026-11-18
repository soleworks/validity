import com.vanniktech.maven.publish.DeploymentValidation
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation

plugins {
    id("validity.kotlin")
    `java-library`
    id("org.jetbrains.kotlinx.kover")
    id("com.vanniktech.maven.publish.base")
}

kotlin {
    explicitApi()

    // The public API is dumped to api/<module>.api, and check fails when it changes without an updated dump:
    // ./gradlew updateKotlinAbi writes it after an intended change.
    @OptIn(ExperimentalAbiValidation::class)
    abiValidation()
}

// Published to Maven Central by the Release workflow. The credentials and the signing key come from the
// ORG_GRADLE_PROJECT_mavenCentralUsername, ..._mavenCentralPassword, ..._signingInMemoryKey and
// ..._signingInMemoryKeyPassword environment variables. The publish ends only when the Central Portal says the
// deployment is on Maven Central, and fails when the Portal rejects it, so a workflow step after it can tell a reader
// the version exists. Asking repo1.maven.org instead is wrong: its CDN caches the 404 of a version asked for before it
// is there, and keeps answering it after the version arrives. SONATYPE_CLOSE_TIMEOUT_SECONDS in gradle.properties
// bounds the wait.
mavenPublishing {
    configure(KotlinJvm(javadocJar = JavadocJar.Empty(), sourcesJar = true))
    publishToMavenCentral(automaticRelease = true, validateDeployment = DeploymentValidation.PUBLISHED)
    signAllPublications()

    pom {
        name = project.name
        description = provider { project.description }
        url = "https://github.com/soleworks/validity"

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }

        developers {
            developer {
                id = "marioalvial"
                name = "Mário Alvial"
                url = "https://github.com/marioalvial"
            }
            developer {
                id = "leonisandes"
                name = "Leoni Sandes"
                url = "https://github.com/leonisandes"
            }
        }

        scm {
            url = "https://github.com/soleworks/validity"
            connection = "scm:git:https://github.com/soleworks/validity.git"
            developerConnection = "scm:git:ssh://git@github.com/soleworks/validity.git"
        }
    }
}
