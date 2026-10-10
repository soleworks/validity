package io.github.soleworks.validity.kotest

import io.github.soleworks.validity.ValidationResult
import io.github.soleworks.validity.Violation
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot

public fun beValid(): Matcher<ValidationResult> = Matcher { result ->
    MatcherResult(
        result.isValid,
        { "Expected a valid result, but it has ${result.violations.described()}" },
        { "Expected an invalid result, but it has no violations" }
    )
}

public fun haveViolation(
    path: String,
    code: String? = null,
    message: String? = null
): Matcher<ValidationResult> = Matcher { result ->
    val expected = describe(path, code, message)

    MatcherResult(
        result.violations.any { it.matches(path, code, message) },
        { "Expected a violation $expected, but the result has ${result.violations.described()}" },
        { "Expected no violation $expected, but the result has ${result.violations.described()}" }
    )
}

public fun ValidationResult.shouldBeValid(): ValidationResult = apply { this should beValid() }

public fun ValidationResult.shouldBeInvalid(): ValidationResult = apply { this shouldNot beValid() }

public fun ValidationResult.shouldHaveViolation(
    path: String,
    code: String? = null,
    message: String? = null
): ValidationResult = apply { this should haveViolation(path, code, message) }

public fun ValidationResult.shouldNotHaveViolation(
    path: String,
    code: String? = null,
    message: String? = null
): ValidationResult = apply { this shouldNot haveViolation(path, code, message) }

private fun Violation.matches(
    path: String,
    code: String?,
    message: String?
): Boolean = this.path == path && (code == null || this.code == code) && (message == null || this.message == message)

private fun describe(
    path: String,
    code: String?,
    message: String?
): String = listOfNotNull(
    "at $path",
    code?.let { "with code $it" },
    message?.let { "with message \"$it\"" }
).joinToString(" ")

private fun List<Violation>.described(): String = when (size) {
    0 -> "no violations"
    1 -> "1 violation: ${single().described()}"
    else -> joinToString(prefix = "$size violations: ") { it.described() }
}

private fun Violation.described(): String = "$path: $message ($code)"
