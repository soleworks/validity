package io.github.soleworks.validity.harness

import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private const val COUNTRY_PACKAGE_PREFIX = "io.github.soleworks.validity.constraints."

private val UPPER_SNAKE_CASE_FORMAT = Regex("""[A-Z][A-Z0-9_]*""")
private val BOOLEAN_NAME_FORMAT = Regex("""(is|has)[A-Z]\w*""")
private val MESSAGE_DEFAULT_FORMAT = Regex("""(messages|phoneMessages)\..+""")
private val TRAILING_LAMBDAS = setOf("block", "selector")
private val TEST_LIBRARIES = listOf("io.kotest", "org.junit", "com.lemonappdev.konsist")

class ProductionConventionsTest {
    private val constraints = production
        .functions()
        .filter { function -> function.hasPublicModifier && function.path.endsWith("Constraints.kt") }

    @Nested
    @DisplayName("Dependencies")
    inner class Dependencies {
        @Test
        fun `every production file should import no test library`() {
            production.files.assertFalse { file ->
                file.imports.any { import -> TEST_LIBRARIES.any { library -> import.name.startsWith(library) } }
            }
        }
    }

    @Nested
    @DisplayName("Constraints")
    inner class Constraints {
        @Test
        fun `every constraint should extend a ValidationNode`() {
            constraints.assertTrue { function ->
                function.receiverType
                    ?.text
                    .orEmpty()
                    .startsWith("ValidationNode<")
            }
        }

        @Test
        fun `every constraint should return Unit`() {
            constraints.assertTrue { function -> function.returnType == null || function.returnType?.text == "Unit" }
        }

        @Test
        fun `every constraint should take a message, or call the overload that does`() {
            constraints.assertTrue { function ->
                constraints.any { overload ->
                    overload.name == function.name && overload.path == function.path && overload.hasMessage()
                }
            }
        }

        @Test
        fun `every message text should default to the configured messages`() {
            constraints.assertTrue { function ->
                val message = function.parameters.firstOrNull { parameter -> parameter.name == "message" }
                val default = message?.defaultValue

                message?.run { type.text } != "String" || default == null || MESSAGE_DEFAULT_FORMAT.matches(default)
            }
        }

        @Test
        fun `every parameter after the message should be a trailing lambda`() {
            constraints.assertTrue { function ->
                function.parameters
                    .dropWhile { parameter -> parameter.name != "message" }
                    .drop(1)
                    .all { parameter -> parameter.name in TRAILING_LAMBDAS }
            }
        }

        @Test
        fun `every constraint file in a country package should be named after the country`() {
            production.files
                .filter { file -> file.packageName.startsWith(COUNTRY_PACKAGE_PREFIX) }
                .assertTrue { file ->
                    file.name.lowercase() == file.packageName.substringAfterLast(
                        '.'
                    ) +
                        "constraints"
                }
        }
    }

    @Nested
    @DisplayName("Names")
    inner class Names {
        @Test
        fun `every function named is or has should return Boolean`() {
            production
                .functions(includeNested = true, includeLocal = true)
                .filter { function -> BOOLEAN_NAME_FORMAT.matches(function.name) }
                .assertTrue { function -> function.returnType?.text == "Boolean" }
        }

        @Test
        fun `every top-level value should be named in upper snake case`() {
            production
                .properties()
                .filter { property -> property.isTopLevel && property.isVal && !property.hasGetter }
                .assertTrue { property -> UPPER_SNAKE_CASE_FORMAT.matches(property.name) }
        }

        @Test
        fun `every top-level regex should be named after the format it matches`() {
            production
                .properties()
                .filter { property -> property.isTopLevel && property.value.orEmpty().startsWith("Regex(") }
                .assertTrue { property -> property.name.endsWith("_FORMAT") }
        }

        @Test
        fun `every top-level list of weights should be named after the check it weights`() {
            production
                .properties()
                .filter { property -> property.isTopLevel && "WEIGHT" in property.name && !property.hasConstModifier }
                .assertTrue { property -> property.name.endsWith("_WEIGHTS") }
        }
    }

    @Nested
    @DisplayName("Layout")
    inner class Layout {
        @Test
        fun `every file should declare its constants before its other values`() {
            production.files.assertTrue { file ->
                val properties = file.properties(includeNested = false)
                val lastConstant = properties.indexOfLast { property -> property.hasConstModifier }
                val firstValue = properties.indexOfFirst { property -> !property.hasConstModifier }

                lastConstant == -1 || firstValue == -1 || lastConstant < firstValue
            }
        }

        @Test
        fun `every file should declare at most one top-level type`() {
            production.files.assertTrue { file -> file.topLevelTypes <= 1 }
        }

        @Test
        fun `every message class should be a DSL receiver that only the library creates`() {
            production.classes().withNameEndingWith("Messages").assertTrue { messages ->
                messages.hasAnnotationWithName(
                    "ValidityDsl"
                ) &&
                    messages.primaryConstructor?.hasInternalModifier == true
            }
        }

        @Test
        fun `every public property of a message class should be a text the user can change`() {
            production
                .classes()
                .withNameEndingWith("Messages")
                .flatMap { messages -> messages.properties() }
                .filter { property -> property.hasPublicModifier }
                .assertTrue { property -> property.isVar && property.type?.text == "String" }
        }
    }
}

private fun KoFunctionDeclaration.hasMessage(): Boolean = parameters.any { parameter -> parameter.name == "message" }
