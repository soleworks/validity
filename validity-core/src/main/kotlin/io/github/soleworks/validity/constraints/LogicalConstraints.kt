package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Constraint
import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.messages

private const val OR = "or"
private const val AND = "and"
private const val NOT = "not"

private val TEMPLATE_PLACEHOLDERS = Regex("\\{left}|\\{right}")

public fun <V> ValidationNode<V>.or(
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = OR,
    message = null,
    passes = ::anyPasses,
    block = block
)

public fun <V> ValidationNode<V>.or(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = OR,
    message = message,
    passes = ::anyPasses,
    block = block
)

public fun <V> ValidationNode<V>.and(
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = AND,
    message = null,
    passes = ::allPass,
    block = block
)

public fun <V> ValidationNode<V>.and(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = AND,
    message = message,
    passes = ::allPass,
    block = block
)

public fun <V> ValidationNode<V>.not(
    message: String = messages.not,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = NOT,
    message = message,
    passes = ::nonePasses,
    block = block
)

private fun anyPasses(branches: List<List<Violation>>): Boolean = branches.isEmpty() || branches.any { it.isEmpty() }

private fun allPass(branches: List<List<Violation>>): Boolean = branches.all { it.isEmpty() }

private fun nonePasses(branches: List<List<Violation>>): Boolean = branches.all { it.isNotEmpty() }

private fun <V> ValidationNode<V>.combine(
    operator: String,
    message: String?,
    passes: (List<List<Violation>>) -> Boolean,
    block: ValidationNode<V>.() -> Unit
) {
    val rules = ValidationNode(path, value).also { it.operator = operator }.apply(block)
    val outer = this.operator
    val template = if (operator == OR) messages.or else messages.and
    val and = messages.and

    add(
        Constraint { path, _ ->
            val branches = rules.branches()

            if (passes(branches))
                null
            else
                Violation(path, message ?: branches.described(operator, outer, template, and), operator)
        }
    )
}

private fun List<List<Violation>>.described(
    operator: String,
    outer: String?,
    template: String,
    and: String
): String {
    val failures = filter { it.isNotEmpty() }
        .map { branch -> branch.described(operator, and) }
        .distinct()
    val text = failures.joined(template)

    return if (failures.size > 1 && outer != null && outer != operator)
        "($text)"
    else
        text
}

private fun List<Violation>.described(
    operator: String,
    and: String
): String {
    val messages = map { it.message }.distinct()
    val text = messages.joined(and)

    return if (messages.size > 1 && operator != AND)
        "($text)"
    else
        text
}

private fun List<String>.joined(template: String): String = reduceOrNull { left, right ->
    TEMPLATE_PLACEHOLDERS.replace(template) { if (it.value == "{left}") left else right }
}.orEmpty()
