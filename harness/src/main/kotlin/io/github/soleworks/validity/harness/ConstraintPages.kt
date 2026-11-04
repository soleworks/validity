package io.github.soleworks.validity.harness

import com.lemonappdev.konsist.api.declaration.KoFunctionDeclaration
import java.io.File

private const val COUNTRY_PACKAGE_PREFIX = "io.github.soleworks.validity.constraints."
private const val SUMMARY_PREFIX = "Checks that "
private const val CALL_LINE_OFFSET = 2
private const val DESCRIPTION_OFFSET = 4

private val SECTION_HEADING_FORMAT = Regex("""^### `(?<name>\w+)`$""")
private val PAGE_HEADING_FORMAT = Regex("""^## """)
private val COUNTRY_HEADING_FORMAT = Regex("""^## .*`constraints\.(?<country>\w+)`$""")
private val TABLE_ROW_FORMAT =
    Regex("""^(?<start>\| \[`(?<name>\w+)`]\(#\w+(?:-(?<occurrence>\d+))?\) \| ).*(?<end> \|)$""")
private val CALL_TYPE_FORMAT = Regex(""" on [^`]*`(?<type>[\w.]+)`""")
private val RECEIVER_TYPE_FORMAT = Regex("""^ValidationNode<(?:out )?(?<type>[\w.]+)""")
private val KDOC_MARGIN_FORMAT = Regex("""^\s*(/\*\*|\*/|\*)?|\*/$""")
private val WHITESPACE_FORMAT = Regex("""\s+""")
private val LINK_FORMAT = Regex("""\[(?<label>[^\]]+)](\[[^\]]+])?""")
private val CODE_NAME_FORMAT = Regex("""[\w.]+""")
private val SUBJECT_FORMAT = Regex(
    "^the (byte array|date and time|string|number|date|time|duration|instant|year|month|value|collection|set|list|" +
        "array|map) "
)

/**
 * The page of [file] in `docs/constraints/` with what the code says of each constraint: the sentence of each
 * section is the first paragraph of the KDoc of the constraint, and its row in the table of the page is the same
 * sentence without "Checks that" and the value it is about.
 *
 * A section is matched with the overload of its name in the package of its country (the `## …` heading that names
 * `constraints.<country>`, or none for the core and the modules) whose receiver is the first type of its call line.
 * A row is matched with the section its link points at: `#dni` the first `dni` of the page, `#dni-2` the second.
 */
internal fun constraintPage(
    file: File
): String {
    val lines = file.readLines()
    val sections = sections(lines)
    val written = lines
        .mapIndexed { index, line -> TABLE_ROW_FORMAT.find(line)?.toRow(sections) ?: line }
        .toMutableList()

    sections.asReversed().forEach { section ->
        written.subList(fromIndex = section.description.first, toIndex = section.description.last + 1).clear()
        written.add(section.description.first, section.summary)
    }

    return buildString { written.forEach(::appendLine) }
}

/** The pages of the constraints, one per kind of value. */
internal val constraintPageFiles: List<File>
    get() = docs
        .resolve("constraints")
        .listFiles { file -> file.extension == "mdx" }
        .orEmpty()
        .sortedBy(File::getName)

private class Section(
    val name: String,
    val summary: String,
    val description: IntRange
)

private fun MatchResult.toRow(
    sections: List<Section>
): String? {
    val occurrence = groups["occurrence"]?.run { value.toInt() } ?: 1
    val section = sections.filter { section -> section.name == groups["name"]?.value }.getOrNull(occurrence - 1)

    return section?.run { groups["start"]?.value + summary.asCheck() + groups["end"]?.value }
}

private fun sections(
    lines: List<String>
): List<Section> {
    var country: String? = null

    return lines.indices.mapNotNull { index ->
        if (PAGE_HEADING_FORMAT.containsMatchIn(lines[index])) country = COUNTRY_HEADING_FORMAT.groupOf(lines[index])
        SECTION_HEADING_FORMAT.groupOf(lines[index])?.let { name ->
            section(lines = lines, heading = index, name = name, country = country)
        }
    }
}

private fun section(
    lines: List<String>,
    heading: Int,
    name: String,
    country: String?
): Section {
    val start = heading + DESCRIPTION_OFFSET
    val end = (start until lines.size).firstOrNull { line -> lines[line].isBlank() } ?: lines.size
    val callType = CALL_TYPE_FORMAT.groupOf(lines[heading + CALL_LINE_OFFSET])

    return Section(name = name, summary = summaryOf(name, country, callType), description = start until end)
}

private fun summaryOf(
    name: String,
    country: String?,
    callType: String?
): String {
    val packageName = country?.let { COUNTRY_PACKAGE_PREFIX + it }
    val overloads = constraints[name].orEmpty().filter { function -> function.isIn(packageName) }
    val overload = overloads.firstOrNull { function -> function.receiverName == callType?.substringAfterLast('.') }
        ?: overloads.firstOrNull()
        ?: error("No public constraint $name in ${packageName ?: "the core or a module"} for its section")

    return overload
        .kDocLines()
        .asSequence()
        .map { line -> line.replace(KDOC_MARGIN_FORMAT, "").trim() }
        .dropWhile(String::isEmpty)
        .takeWhile(String::isNotEmpty)
        .joinToString(separator = " ")
        .replace(WHITESPACE_FORMAT, " ")
        .replace(LINK_FORMAT) { link ->
            link.groups["label"]
                ?.value
                .orEmpty()
                .inBackticksWhenCode()
        }
}

private val constraints: Map<String, List<KoFunctionDeclaration>> by lazy {
    production
        .functions()
        .filter { function -> function.hasPublicModifier && function.path.endsWith("Constraints.kt") }
        .groupBy { function -> function.name }
}

private fun Regex.groupOf(
    line: String
): String? = find(line)?.groups?.get(1)?.value

private fun KoFunctionDeclaration.isIn(
    countryPackage: String?
): Boolean {
    val name = packagee?.name.orEmpty()

    return countryPackage?.let { name == it } ?: !name.startsWith(COUNTRY_PACKAGE_PREFIX)
}

private fun KoFunctionDeclaration.kDocLines(): List<String> {
    val lines = File(path).readLines()
    val declaration = location.substringBeforeLast(':').substringAfterLast(':').toInt() - 1
    val start = (declaration downTo 0).first { line -> lines[line].trim().startsWith("/**") }
    val end = (start until lines.size).first { line -> lines[line].trim().endsWith("*/") }

    return lines.subList(fromIndex = start, toIndex = end + 1)
}

private val KoFunctionDeclaration.receiverName: String?
    get() = RECEIVER_TYPE_FORMAT.groupOf(receiverType?.text.orEmpty())?.substringAfterLast('.')

private fun String.inBackticksWhenCode(): String = if (CODE_NAME_FORMAT.matches(this)) "`$this`" else this

private fun String.asCheck(): String = removePrefix(SUMMARY_PREFIX)
    .replaceFirst(SUBJECT_FORMAT, "")
    .replaceFirstChar(Char::uppercaseChar)
