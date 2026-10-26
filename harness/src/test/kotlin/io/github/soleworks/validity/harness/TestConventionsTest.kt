package io.github.soleworks.validity.harness

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private const val CONSTRAINTS_PACKAGE = "io.github.soleworks.validity.constraints"

private val NESTED_NAME_FORMAT = Regex("""@DisplayName\("When (\w+) is called"\)""")
private val TEST_NAME_FORMAT = Regex("""given .+ should .+""")
private val WHEN_FORMAT = Regex("""val (violations|result|error) = .+|\w+\.validate\(\)""")
private val ACTION_ASSERTION_FORMAT = Regex("""should(Throw<\w+>|NotThrowAny) \{.*""")
private val POSITIONAL_ARGUMENTS_FORMAT = Regex("""\b(Violation|ValidationNode(<[^>]+>)?)\("""")
private val TEST_ANNOTATIONS = listOf("Test", "ParameterizedTest")
private val CATEGORY_FORMATS = listOf(
    Regex(""" should (accept|be valid|keep|not throw)"""),
    Regex(""" should (report|fail)"""),
    Regex(""" should (skip|not validate|not count|ignore)"""),
    Regex(""" should (build|replace|fill|not fill|join|describe|use)"""),
    Regex(""" should throw""")
)

class TestConventionsTest {
    private val testClasses = tests
        .classes()
        .filter { testClass -> testClass.isTopLevel && testClass.name.endsWith("Test") }
    private val nestedClasses = testClasses.flatMap { testClass -> testClass.classes(includeNested = false) }
    private val testFunctions = tests
        .functions(includeNested = true)
        .filter { function -> function.hasAnnotationWithName(TEST_ANNOTATIONS) }

    @Nested
    @DisplayName("Classes")
    inner class Classes {
        @Test
        fun `every test class should test a source file of the same package`() {
            testClasses.assertTrue { testClass -> production.sourceOf(testClass.containingFile) != null }
        }

        @Test
        fun `every test file should declare at most one top-level type`() {
            tests.files.assertTrue { file -> file.topLevelTypes <= 1 }
        }

        @Test
        fun `every test class should hold its tests in nested classes`() {
            testClasses.assertFalse { testClass ->
                testClass
                    .functions(includeNested = false)
                    .any { function -> function.hasAnnotationWithName(TEST_ANNOTATIONS) }
            }
        }

        @Test
        fun `every nested class should be an inner class`() {
            nestedClasses.assertTrue { nested -> nested.hasAnnotationWithName("Nested") && nested.hasInnerModifier }
        }

        @Test
        fun `every nested class should be named after the function it tests`() {
            nestedClasses.assertTrue { nested ->
                nested.name == nested.testedFunction().replaceFirstChar(Char::uppercase)
            }
        }

        @Test
        fun `every nested class should test a function of the source file`() {
            nestedClasses.assertTrue { nested ->
                val functions = production.sourceOf(nested.containingFile)?.functions(includeNested = true)

                functions.orEmpty().any { function -> function.name == nested.testedFunction() }
            }
        }

        @Test
        fun `every nested class should list its tests by what they check`() {
            nestedClasses.assertTrue { nested ->
                val categories = nested
                    .functions(includeNested = false)
                    .filter { function -> function.hasAnnotationWithName(TEST_ANNOTATIONS) }
                    .mapNotNull { function -> category(function.name) }

                categories == categories.sorted()
            }
        }
    }

    @Nested
    @DisplayName("Tests")
    inner class Tests {
        @Test
        fun `every test should be named after a situation and an outcome`() {
            testFunctions.assertTrue { function -> TEST_NAME_FORMAT.matches(function.name) }
        }

        @Test
        fun `every test should have a block body`() {
            testFunctions.assertTrue { function -> function.hasBlockBody && function.returnType == null }
        }

        @Test
        fun `every test should be a Given, a When and a Then`() {
            testFunctions.assertTrue { function ->
                val blocks = function.bodyBlocks()
                val actions = blocks.dropLast(1).filter { block -> WHEN_FORMAT.matches(block) }

                if (ACTION_ASSERTION_FORMAT.matches(blocks.last()))
                    blocks.size <= 2 && actions.isEmpty()
                else
                    blocks.size in 2..3 && actions.size == 1 && WHEN_FORMAT.matches(blocks[blocks.size - 2])
            }
        }

        @Test
        fun `every test should end in one assertion`() {
            testFunctions.assertTrue { function -> "should" in function.bodyBlocks().last() }
        }

        @Test
        fun `every violation and node should name its arguments`() {
            tests.files.assertFalse { file -> POSITIONAL_ARGUMENTS_FORMAT.containsMatchIn(file.text) }
        }

        @Test
        fun `every constraint test should build a ValidationNode rather than validate a sample`() {
            tests.files
                .filter { file -> file.packageName.startsWith(CONSTRAINTS_PACKAGE) }
                .assertFalse { file ->
                    file.imports.any { import ->
                        import.name ==
                            "io.github.soleworks.validity.validate"
                    }
                }
        }
    }
}

private fun KoClassDeclaration.testedFunction(): String = annotations
    .firstNotNullOfOrNull { annotation -> NESTED_NAME_FORMAT.matchEntire(annotation.text) }
    ?.run { groupValues[1] }
    .orEmpty()

private fun category(
    name: String
): Int? = CATEGORY_FORMATS.indexOfFirst { format -> format.containsMatchIn(name) }.takeIf { index -> index >= 0 }

private fun KoFunctionDeclaration.bodyBlocks(): List<String> = text
    .lines()
    .dropWhile { line -> !line.trimEnd().endsWith(") {") }
    .drop(1)
    .dropLast(1)
    .map(String::trim)
    .fold(listOf(emptyList<String>()), ::withLine)
    .filter { block -> block.isNotEmpty() }
    .map { block -> block.joinToString(separator = " ") }

private fun withLine(
    blocks: List<List<String>>,
    line: String
): List<List<String>> = if (line.isEmpty())
    blocks + listOf(emptyList())
else
    blocks.dropLast(1) + listOf(blocks.last() + line)
