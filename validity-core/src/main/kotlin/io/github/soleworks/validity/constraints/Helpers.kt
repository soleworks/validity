package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import java.time.LocalDate

internal fun String.digits(): List<Int> = filter(Char::isDigit).map(Char::digitToInt)

internal fun List<Int>.weightedSum(
    weights: List<Int>
): Int = zip(weights) { digit, weight -> digit * weight }.sum()

internal fun Regex.groups(
    text: String
): List<String>? = matchEntire(text)?.run { groupValues.drop(1) }

internal fun isDate(
    year: Int,
    month: Int,
    day: Int
): Boolean = runCatching { LocalDate.of(year, month, day) }.isSuccess

internal fun <V, A : Any> ValidationNode<V>.constraint(
    argument: A?,
    message: suspend V.(A) -> String,
    code: String,
    predicate: suspend (V, A) -> Boolean
) {
    if (argument == null)
        return

    constraint(
        message = { it.message(argument) },
        code = code,
        predicate = { predicate(it, argument) }
    )
}

internal fun <V, A : Any, B : Any> ValidationNode<V>.constraint(
    first: A?,
    second: B?,
    message: suspend V.(A, B) -> String,
    code: String,
    predicate: suspend (V, A, B) -> Boolean
) {
    if (first == null || second == null)
        return

    constraint(
        message = { it.message(first, second) },
        code = code,
        predicate = { predicate(it, first, second) }
    )
}
