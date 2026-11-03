package io.github.soleworks.validity.harness

private val SAMPLE_TITLE_FORMAT = Regex("""^```kotlin (\w+\.kt)$""")

/**
 * A fenced code block of a docs page.
 *
 * @property header the opening line, with the language and the title, such as ```` ```kotlin Account.kt ````.
 * @property code the lines of code, without the blank lines around them.
 */
class CodeBlock(
    val header: String,
    val code: List<String>
) {
    /** The sample file the block shows, such as `Account.kt`, or `null` for a block that is not a sample. */
    val sample: String?
        get() = SAMPLE_TITLE_FORMAT.find(header)?.run { groupValues[1] }
}
