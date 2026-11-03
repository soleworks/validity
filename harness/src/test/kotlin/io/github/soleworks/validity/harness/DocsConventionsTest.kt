package io.github.soleworks.validity.harness

import io.kotest.matchers.shouldBe
import java.io.File
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

private const val CORE_SOURCES = "validity-core/src/main/kotlin/io/github/soleworks/validity"
private const val PHONE_SOURCES = "validity-phone/src/main/kotlin/io/github/soleworks/validity/phone"
private const val COUNTRY_PACKAGE_PREFIX = "io.github.soleworks.validity.constraints."
private const val MAX_DESCRIPTION_LENGTH = 160

private val CONSTRAINT_HEADING_FORMAT = Regex("""^### `(\w+)`""", RegexOption.MULTILINE)
private val CONSTRAINT_SECTION_FORMAT = Regex(
    """^### `(\w+)`(.*?)(?=^#{2,3} |\z)""",
    setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL)
)
private val CONSTRAINT_FACTS_FORMAT = Regex(
    """<ConstraintFacts code="(\w+)"(?: message="([^"]*)" change='(?:(\w+) \{ )?(\w+) = "…"(?: })?')? />"""
)
private val MESSAGE_PROPERTY_FORMAT = Regex("""public var (\w+): String = "([^"]*)"""")
private val MESSAGE_DEFAULT_FORMAT = Regex("""message: String = (messages|phoneMessages)\.""")
private val VARIABLE_USE_FORMAT = Regex("""\{\{(\w+)}}""")
private val SNIPPET_IMPORT_FORMAT = Regex("""^import \w+ from "/snippets/([\w-]+\.mdx)";""", RegexOption.MULTILINE)
private val NAVIGATION_PAGES_FORMAT = Regex(""""pages":\s*\[(.*?)]""", RegexOption.DOT_MATCHES_ALL)
private val QUOTED_FORMAT = Regex(""""([^"]+)"""")
private val VERSION_FORMAT = Regex("""^version=([\d.]+)""", RegexOption.MULTILINE)
private val KOTLIN_VERSION_FORMAT = Regex("""^kotlin = "(\d+\.\d+)""", RegexOption.MULTILINE)
private val JAVA_VERSION_FORMAT = Regex("""JavaLanguageVersion\.of\((\d+)\)""")
private val POSTAL_CODE_FORMAT = Regex(""""[A-Z]{2}" to """)
private val CSS_CLASS_FORMAT = Regex("""\.(v-[a-z0-9-]+)""")
private val CLASS_NAME_FORMAT = Regex("""className="([^"]*)"""")
private val INLINE_STYLE_FORMAT = Regex("""\sstyle=[{"]""")
private val COLOR_ATTRIBUTE_FORMAT = Regex("""="#[0-9a-fA-F]{3,8}"""")
private val FACT_WRITTEN_BY_HAND_FORMATS = listOf(
    Regex("""\bKotlin \d"""),
    Regex("""\b(Java|JVM) \d"""),
    Regex("""\b\d+ (built-in )?constraints\b"""),
    Regex("""\b\d+ countries\b"""),
    Regex("""\b(codes|documents|formats) of \d+\b"""),
    Regex("""soleworks\.mintlify\.site"""),
    Regex("""io\.github\.soleworks:validity-[a-z]+:\d""")
)

class DocsConventionsTest {
    private val sources = docsPages + docsSnippets

    @Nested
    @DisplayName("Samples")
    inner class Samples {
        @Test
        fun `every code block named after a Kotlin file should be the code of a sample of that name`() {
            sources
                .asSequence()
                .flatMap { source -> source.codeBlocks.map { block -> source to block } }
                .filter { (_, block) -> block.sample != null }
                .filterNot { (_, block) -> block.isTheCodeOfItsSample() }
                .map { (source, block) -> "${source.docsPath}: ${block.sample.orEmpty()}" }
                .toList()
                .shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Facts")
    inner class Facts {
        @Test
        fun `the version variable should be the version the build publishes`() {
            val properties = repository.resolve("gradle.properties").readText()

            docsVariables["version"] shouldBe VERSION_FORMAT.find(properties)?.run { groupValues[1] }
        }

        @Test
        fun `the kotlin variable should be the Kotlin version the build uses`() {
            val catalog = repository.resolve("gradle/libs.versions.toml").readText()

            docsVariables["kotlin"] shouldBe KOTLIN_VERSION_FORMAT.find(catalog)?.run { groupValues[1] }
        }

        @Test
        fun `the java variable should be the Java version the build targets`() {
            val convention = repository.resolve("buildSrc/src/main/kotlin/validity.kotlin.gradle.kts").readText()

            docsVariables["java"] shouldBe JAVA_VERSION_FORMAT.find(convention)?.run { groupValues[1] }
        }

        @Test
        fun `the constraints variable should be the number of public constraints`() {
            val constraints = publicConstraints()
                .map { function -> function.name }
                .toSet()

            docsVariables["constraints"] shouldBe constraints.size.toString()
        }

        @Test
        fun `the postalCountries variable should be the number of countries postalCode knows`() {
            val location = repository.resolve("$CORE_SOURCES/constraints/LocationConstraints.kt").readText()

            docsVariables["postalCountries"] shouldBe POSTAL_CODE_FORMAT.findAll(location).count().toString()
        }

        @Test
        fun `the documentCountries variable should be the number of country packages`() {
            val countries = production
                .files
                .map { file -> file.packageName }
                .filter { packageName -> packageName.startsWith(COUNTRY_PACKAGE_PREFIX) }
                .toSet()

            docsVariables["documentCountries"] shouldBe countries.size.toString()
        }

        @Test
        fun `every page should write a fact through its variable`() {
            sources
                .asSequence()
                .flatMap { source -> source.factsWrittenByHand() }
                .toList()
                .shouldListNothing()
        }

        @Test
        fun `every variable a page uses should be defined in docs json`() {
            sources
                .asSequence()
                .flatMap { source -> source.variablesUsed().map { name -> source to name } }
                .filterNot { (_, name) -> name in docsVariables }
                .map { (source, name) -> "${source.docsPath}: $name" }
                .toList()
                .shouldListNothing()
        }

        @Test
        fun `no frontmatter should use a variable, since Mintlify does not fill it in there`() {
            sources
                .filter { source -> source.frontmatter.values.any(VARIABLE_USE_FORMAT::containsMatchIn) }
                .map { source -> source.docsPath }
                .shouldListNothing()
        }

        @Test
        fun `the skill should state the facts of the variables, since Mintlify does not fill them in it`() {
            val skill = docs.resolve("skill.md").readText()
            val facts = listOf(
                docsVariables["site"].orEmpty(),
                "Kotlin ${docsVariables["kotlin"].orEmpty()}",
                "Java ${docsVariables["java"].orEmpty()}"
            )

            facts
                .filterNot { fact -> fact in skill }
                .shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Constraints")
    inner class Constraints {
        private val constraintPages = docsPages.filter { page -> page.parentFile.name == "constraints" }

        @Test
        fun `every public constraint should have a section in the constraints pages`() {
            val sections = constraintPages
                .flatMap { page -> page.constraintHeadings() }
                .toSet()

            (publicConstraints().map { function -> function.name }.toSet() - sections).shouldListNothing()
        }

        @Test
        fun `every constraint section should state its facts with ConstraintFacts`() {
            constraintSections()
                .filter { section -> section.facts == null }
                .map { section -> "${section.page.docsPath}: ${section.name}" }
                .shouldListNothing()
        }

        @Test
        fun `every constraint section should give the code of its violation, which is the name of the constraint`() {
            constraintSections()
                .filter { section -> section.facts != null && section.facts[1] != section.name }
                .map { section -> "${section.page.docsPath}: ${section.name}" }
                .shouldListNothing()
        }

        @Test
        fun `every constraint section with a default message should give it, as the messages class holds it`() {
            val withDefault = publicConstraints()
                .filter { function -> MESSAGE_DEFAULT_FORMAT.containsMatchIn(function.text) }
                .map { function -> function.name }
                .toSet()
            val messages = defaultMessages()

            constraintSections()
                .mapNotNull { section -> section.messageMismatch(withDefault, messages) }
                .shouldListNothing()
        }

        private fun constraintSections(): List<ConstraintSection> = constraintPages.flatMap { page ->
            CONSTRAINT_SECTION_FORMAT
                .findAll(page.content)
                .map { section -> section.toConstraintSection(page) }
        }
    }

    @Nested
    @DisplayName("Messages")
    inner class Messages {
        @Test
        fun `the template of the messages guide should give every message with its default`() {
            val page = docs.resolve("guides/messages.mdx").readText()

            defaultMessages()
                .filterNot { (place, text) -> """${place.second} = "$text"""" in page }
                .map { (place, text) -> "${place.first.ifEmpty { "messages" }}.${place.second}: $text" }
                .shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Repetition")
    inner class Repetition {
        @Test
        fun `no code block should be written twice, except a sample, which its file keeps in one place`() {
            sources
                .asSequence()
                .flatMap { source -> source.codeBlocks.map { block -> block to source.docsPath } }
                .filter { (block, _) -> block.sample == null && block.isSubstantial }
                .groupBy(keySelector = { (block, _) -> block.code }, valueTransform = { (_, place) -> place })
                .filterValues { places -> places.size > 1 }
                .map { (code, places) -> "${code.first()} in ${places.joinToString()}" }
                .shouldListNothing()
        }

        @Test
        fun `no sentence should be written twice, since a fact said twice drifts apart`() {
            sources
                .asSequence()
                .flatMap { source -> source.sentences.map { sentence -> sentence to source.docsPath } }
                .groupBy(keySelector = { (sentence, _) -> sentence }, valueTransform = { (_, place) -> place })
                .filterValues { places -> places.size > 1 }
                .map { (sentence, places) -> """"$sentence" in ${places.joinToString()}""" }
                .shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Snippets")
    inner class Snippets {
        @Test
        fun `every snippet should be imported by a page`() {
            val imported = sources
                .flatMap { source -> source.snippetsImported() }
                .toSet()

            docsSnippets
                .map { snippet -> snippet.name }
                .filterNot { name -> name in imported }
                .shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Styles")
    inner class Styles {
        private val definedClasses = docs
            .listFiles { file -> file.extension == "css" }
            .orEmpty()
            .flatMap { sheet -> CSS_CLASS_FORMAT.findAll(sheet.readText()).map { match -> match.groupValues[1] } }
            .toSet()
        private val usedClasses = sources
            .asSequence()
            .flatMap { source -> source.classNames() }
            .filter(String::isNotBlank)
            .toSet()

        @Test
        fun `no page should style an element inline, since the stylesheets hold every style`() {
            sources
                .filter { source -> INLINE_STYLE_FORMAT.containsMatchIn(source.content) }
                .map { source -> source.docsPath }
                .shouldListNothing()
        }

        @Test
        fun `no page should write a color, since tokens css holds every color`() {
            sources
                .flatMap { source -> source.colorsWritten() }
                .shouldListNothing()
        }

        @Test
        fun `every class a page uses should be a class of the stylesheets`() {
            (usedClasses - definedClasses).shouldListNothing()
        }

        @Test
        fun `every class of the stylesheets should be used by a page`() {
            (definedClasses - usedClasses).shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Pages")
    inner class Pages {
        @Test
        fun `every page should have a title and a description`() {
            docsPages
                .filter { page -> page.lacksTitleOrDescription() }
                .map { page -> page.docsPath }
                .shouldListNothing()
        }

        @Test
        fun `every description should fit in a search result`() {
            docsPages
                .filter { page -> page.frontmatter["description"].orEmpty().length > MAX_DESCRIPTION_LENGTH }
                .map { page -> "${page.docsPath}: ${page.frontmatter["description"].orEmpty().length} characters" }
                .shouldListNothing()
        }
    }

    @Nested
    @DisplayName("Navigation")
    inner class Navigation {
        private val listed = NAVIGATION_PAGES_FORMAT
            .findAll(docs.resolve("docs.json").readText())
            .flatMap { pages -> QUOTED_FORMAT.findAll(pages.groupValues[1]).map { page -> page.groupValues[1] } }
            .toSet()

        @Test
        fun `every page of the navigation should be a page of the docs`() {
            listed
                .filterNot { page -> docs.resolve("$page.mdx").isFile }
                .shouldListNothing()
        }

        @Test
        fun `every page of the docs should be in the navigation`() {
            docsPages
                .map { page -> page.docsPath.removeSuffix(".mdx") }
                .filterNot { page -> page in listed }
                .shouldListNothing()
        }
    }
}

private class ConstraintSection(
    val page: File,
    val name: String,
    text: String
) {
    /** The groups of its ConstraintFacts (code, message, block and name of the message), or `null` without one. */
    val facts: List<String>? = CONSTRAINT_FACTS_FORMAT.find(text)?.groupValues

    fun messageMismatch(
        withDefault: Set<String>,
        messages: Map<Pair<String, String>, String>
    ): String? {
        val (message, block, key) = facts?.drop(2) ?: return null
        val expected = messages[block to key]
        val where = "${page.docsPath}: $name"

        return when {
            message.isEmpty() && name in withDefault -> "$where has no message"
            message.isEmpty() -> null
            expected == null -> "$where points at no message $block.$key"
            expected != message -> """$where says "$message", the code "$expected""""
            else -> null
        }
    }
}

private fun publicConstraints() = production
    .functions()
    .filter { function -> function.hasPublicModifier && function.path.endsWith("Constraints.kt") }

private fun defaultMessages(): Map<Pair<String, String>, String> {
    val files = repository
        .resolve(CORE_SOURCES)
        .listFiles { file -> file.name.endsWith("Messages.kt") }
        .orEmpty()
        .toList() + repository.resolve("$PHONE_SOURCES/PhoneMessages.kt")

    return files
        .asSequence()
        .flatMap { file -> file.messageDefaults() }
        .toMap()
}

private fun MatchResult.toConstraintSection(
    page: File
) = ConstraintSection(page = page, name = groupValues[1], text = groupValues[2])

private fun CodeBlock.isTheCodeOfItsSample(): Boolean =
    samples.any { file -> file.name == sample && file.sampleCode() == code }

private fun File.factsWrittenByHand(): List<String> = FACT_WRITTEN_BY_HAND_FORMATS.flatMap { format ->
    format.findAll(content).map { fact -> "$docsPath: ${fact.value}" }.toList()
}

private fun File.variablesUsed(): Sequence<String> =
    VARIABLE_USE_FORMAT.findAll(content).map { use -> use.groupValues[1] }

private fun File.constraintHeadings(): Sequence<String> =
    CONSTRAINT_HEADING_FORMAT.findAll(content).map { heading -> heading.groupValues[1] }

private fun File.snippetsImported(): Sequence<String> =
    SNIPPET_IMPORT_FORMAT.findAll(content).map { import -> import.groupValues[1] }

private fun File.classNames(): Sequence<String> =
    CLASS_NAME_FORMAT.findAll(content).flatMap { match -> match.groupValues[1].split(" ") }

private fun File.colorsWritten(): Sequence<String> =
    COLOR_ATTRIBUTE_FORMAT.findAll(content).map { color -> "$docsPath: ${color.value}" }

private fun File.lacksTitleOrDescription(): Boolean =
    listOf("title", "description").any { field -> frontmatter[field].isNullOrBlank() }

private fun File.messageDefaults(): Sequence<Pair<Pair<String, String>, String>> {
    val block = name.removeSuffix("Messages.kt").replaceFirstChar(Char::lowercase)

    return MESSAGE_PROPERTY_FORMAT.findAll(readText()).map { property -> property.toMessageDefault(block) }
}

private fun MatchResult.toMessageDefault(
    block: String
): Pair<Pair<String, String>, String> = (block to groupValues[1]) to groupValues[2]
