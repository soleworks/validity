package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.github.soleworks.validity.messages

public fun <V> ValidationNode<V>.or(
    block: ValidationNode<V>.() -> Unit
) {
    val template = messages.or

    or(
        message = { failures -> failures.joined(template) },
        block = block
    )
}

public fun <V> ValidationNode<V>.or(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = or(
    message = { message },
    block = block
)

private fun <V> ValidationNode<V>.or(
    message: (List<String>) -> String,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    code = "or",
    message = message,
    passes = { branches -> branches.any { it.isEmpty() } },
    block = block
)

public fun <V> ValidationNode<V>.and(
    block: ValidationNode<V>.() -> Unit
) {
    val template = messages.and

    and(
        message = { failures -> failures.joined(template) },
        block = block
    )
}

public fun <V> ValidationNode<V>.and(
    message: String,
    block: ValidationNode<V>.() -> Unit
): Unit = and(
    message = { message },
    block = block
)

private fun <V> ValidationNode<V>.and(
    message: (List<String>) -> String,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    code = "and",
    message = message,
    passes = { branches -> branches.all { it.isEmpty() } },
    block = block
)

public fun <V> ValidationNode<V>.not(
    message: String = messages.not,
    block: ValidationNode<V>.() -> Unit
): Unit = combine(
    code = "not",
    message = { message },
    passes = { branches -> branches.all { it.isNotEmpty() } },
    block = block
)

private fun <V> ValidationNode<V>.combine(
    code: String,
    message: (List<String>) -> String,
    passes: (List<List<Violation>>) -> Boolean,
    block: ValidationNode<V>.() -> Unit
) {
    val rules = ValidationNode(path, value).apply(block)
    val template = messages.and

    constraint(
        message = { message(rules.failures(template)) },
        code = code,
        predicate = { passes(rules.branches()) }
    )
}

private fun ValidationNode<*>.failures(
    template: String
): List<String> = branches()
    .filter { it.isNotEmpty() }
    .map { branch -> branch.map(Violation::message).joined(template) }

private fun List<String>.joined(template: String): String = reduceOrNull { left, right ->
    template
        .replace("{left}", left)
        .replace("{right}", right)
}.orEmpty()
