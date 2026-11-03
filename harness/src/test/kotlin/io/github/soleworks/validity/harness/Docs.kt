package io.github.soleworks.validity.harness

import io.kotest.matchers.shouldBe
import java.io.File

private const val MIN_DUPLICATE_WORDS = 6
private const val MIN_DUPLICATE_LINES = 3

private val PARAGRAPH_BREAK_FORMAT = Regex("""\R\s*\R""")
private val SENTENCE_BREAK_FORMAT = Regex("""(?<=[.!?])\s+""")
private val WHITESPACE_FORMAT = Regex("""\s+""")
private val FRONTMATTER_FORMAT = Regex("""\A---\R(.*?)\R---\R""", RegexOption.DOT_MATCHES_ALL)
private val FRONTMATTER_FIELD_FORMAT = Regex("""^(\w+):\s*"?(.*?)"?\s*$""", RegexOption.MULTILINE)
private val FENCED_BLOCK_FORMAT =
    Regex("""^```(\S*)[^\n]*\R(.*?)\R```""", setOf(RegexOption.MULTILINE, RegexOption.DOT_MATCHES_ALL))
private val VARIABLES_FORMAT = Regex(""""variables":\s*\{(.*?)}""", RegexOption.DOT_MATCHES_ALL)
private val JSON_PAIR_FORMAT = Regex(""""(\w+)":\s*"([^"]*)"""")
private val NOT_PROSE_FORMAT = Regex("""^\s*(```|\||<|import |#|\{/\*|---)""")

/** The root of the repository, which the harness build passes in. */
val repository: File = File(System.getProperty("validity.root"))

/** The content directory of the docs site, where `docs.json` is. */
val docs: File = repository.resolve("docs")

/** Every page of the site, without the snippets. */
val docsPages: List<File> = docs
    .walkTopDown()
    .filter { file -> file.extension == "mdx" && !file.isSnippet }
    .toList()

/** The reusable pieces in `docs/snippets`, imported by the pages. */
val docsSnippets: List<File> = docs
    .resolve("snippets")
    .walkTopDown()
    .filter { file -> file.extension == "mdx" }
    .toList()

/** The global variables of `docs.json`, which Mintlify fills in as `{{name}}` when it builds the site. */
val docsVariables: Map<String, String> = VARIABLES_FORMAT
    .find(docs.resolve("docs.json").readText())
    ?.run { JSON_PAIR_FORMAT.findAll(groupValues[1]).associate { pair -> pair.groupValues[1] to pair.groupValues[2] } }
    .orEmpty()

/** The sample files of the library modules, the only code a titled block of the docs may show. */
val samples: List<File> = repository
    .listFiles { file -> file.name.startsWith("validity-") }
    .orEmpty()
    .asSequence()
    .flatMap { module -> module.resolve("src/test/kotlin").walkTopDown() }
    .filter { file -> file.extension == "kt" && file.invariantSeparatorsPath.contains("/samples/") }
    .toList()

val File.isSnippet: Boolean
    get() = invariantSeparatorsPath.contains("/docs/snippets/")

/** The path of the page from the content directory, as the build reports it. */
val File.docsPath: String
    get() = relativeTo(docs).invariantSeparatorsPath

/** The fields of the frontmatter of the page, such as `title` and `description`. */
val File.frontmatter: Map<String, String>
    get() = FRONTMATTER_FORMAT
        .find(readText())
        ?.run { fieldsOf(groupValues[1]) }
        .orEmpty()

/** The page without its frontmatter. */
val File.content: String
    get() = readText().replaceFirst(FRONTMATTER_FORMAT, "")

/** The fenced code blocks of the page, each with its language, title line and code. */
val File.codeBlocks: List<CodeBlock>
    get() = FENCED_BLOCK_FORMAT
        .findAll(content)
        .map { block -> block.toCodeBlock() }
        .toList()

/**
 * The sentences of prose of the page, long enough to state a fact: no code, tables, headings, components or imports.
 * A sentence ends with a period, a question mark or an exclamation mark, as a paragraph does.
 */
val File.sentences: List<String>
    get() = content
        .replace(FENCED_BLOCK_FORMAT, "")
        .split(PARAGRAPH_BREAK_FORMAT)
        .asSequence()
        .map { block -> block.lines().filterNot { line -> NOT_PROSE_FORMAT.containsMatchIn(line) }.joinToString(" ") }
        .flatMap { paragraph -> paragraph.replace(WHITESPACE_FORMAT, " ").trim().split(SENTENCE_BREAK_FORMAT) }
        .filter { sentence -> sentence.split(" ").size >= MIN_DUPLICATE_WORDS }
        .toList()

/** Whether the block is long enough to count as repeated code. */
val CodeBlock.isSubstantial: Boolean
    get() = code.count(String::isNotBlank) >= MIN_DUPLICATE_LINES

/** The code of a sample, without its package and imports, as a titled block shows it. */
fun File.sampleCode(): List<String> = readLines()
    .filterNot { line -> line.startsWith("package ") || line.startsWith("import ") }
    .trimmed()

private fun fieldsOf(
    frontmatter: String
): Map<String, String> = FRONTMATTER_FIELD_FORMAT
    .findAll(frontmatter)
    .associate { field -> field.groupValues[1] to field.groupValues[2] }

private fun MatchResult.toCodeBlock() =
    CodeBlock(header = value.lineSequence().first(), code = groupValues[2].lines().trimmed())

fun List<String>.trimmed(): List<String> = dropWhile(String::isBlank).dropLastWhile(String::isBlank)

/** Asserts that the check found nothing, printing every finding when it did, not only the first. */
fun Collection<Any>.shouldListNothing() {
    toList() shouldBe emptyList()
}
