package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.Constraint
import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

@PublishedApi
internal const val OR: String = "or"

@PublishedApi
internal const val AND: String = "and"

@PublishedApi
internal const val NOT: String = "not"

public inline fun <V> ValidationNode<V>.or(
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = OR,
    message = null,
    rules = branch(OR).apply(block)
)

public inline fun <V> ValidationNode<V>.or(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = OR,
    message = message,
    rules = branch(OR).apply(block)
)

public inline fun <V> ValidationNode<V>.and(
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = AND,
    message = null,
    rules = branch(AND).apply(block)
)

public inline fun <V> ValidationNode<V>.and(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = AND,
    message = message,
    rules = branch(AND).apply(block)
)

public inline fun <V> ValidationNode<V>.not(
    message: String = messages.not,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    operator = NOT,
    message = message,
    rules = branch(NOT).apply(block)
)

private fun anyPasses(
    branches: List<List<Violation>>
): Boolean = branches.isEmpty() || branches.any { it.isEmpty() }

private fun allPass(
    branches: List<List<Violation>>
): Boolean = branches.all { it.isEmpty() }

private fun nonePasses(
    branches: List<List<Violation>>
): Boolean = branches.all { it.isNotEmpty() }

@PublishedApi
internal fun <V> ValidationNode<V>.branch(
    operator: String
): ValidationNode<V> = ValidationNode(path, value).also { it.operator = operator }

@PublishedApi
internal fun <V> ValidationNode<V>.combine(
    operator: String,
    message: String?,
    rules: ValidationNode<V>
) {
    val passes = when (operator) {
        OR -> ::anyPasses
        AND -> ::allPass
        else -> ::nonePasses
    }
    val outer = this.operator
    val metadata = metadata
    val template = if (operator == OR) messages.or else messages.and
    val and = messages.and

    add(
        Constraint { path, value ->
            val branches = rules.relocated(path).branches()

            if (passes(branches))
                null
            else
                Violation(
                    path = path,
                    message = message?.filled("path" to path, "value" to value) ?: branches.described(
                        operator = operator,
                        outer = outer,
                        template = template.filled("path" to path, "value" to value),
                        and = and.filled("path" to path, "value" to value)
                    ),
                    code = operator,
                    metadata = metadata
                )
        }
    )
}

private fun List<List<Violation>>.described(
    operator: String,
    outer: String?,
    template: String,
    and: String
): String {
    val failures = asSequence()
        .filter { it.isNotEmpty() }
        .map { branch -> branch.described(operator = operator, and = and) }
        .distinct()
        .toList()
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

private fun List<String>.joined(
    template: String
): String = reduceOrNull { left, right -> template.filled("left" to left, "right" to right) }.orEmpty()
