package io.github.soleworks.validity

import kotlin.reflect.KProperty0
import kotlinx.coroutines.runBlocking

@ValidityDsl
public class Validation internal constructor(
    private val block: suspend Validation.() -> Unit
) {
    internal val nodes = mutableListOf<ValidationNode<*>>()

    @PublishedApi
    internal var metadata: Map<String, Any?> = emptyMap()

    public fun add(
        node: ValidationNode<*>
    ) {
        nodes += node
    }

    public fun constraint(
        property: KProperty0<*>,
        message: String,
        code: String = "",
        predicate: suspend () -> Boolean
    ): Unit = node(path = property.name, value = property.get()).constraint(
        message = message,
        code = code,
        predicate = { predicate() }
    )

    public fun constraint(
        path: String = "",
        message: String,
        code: String = "",
        predicate: suspend () -> Boolean
    ): Unit = node(path = path, value = null).constraint(
        message = message,
        code = code,
        predicate = { predicate() }
    )

    public inline fun metadata(
        vararg entries: Pair<String, Any?>,
        block: Validation.() -> Unit
    ) {
        val outer = metadata

        metadata = outer + entries

        try {
            block()
        } finally {
            metadata = outer
        }
    }

    public suspend fun validate(
        validity: Validity = Validity.default
    ): ValidationResult = validity.use {
        ValidationResult(
            violations = built().nodes.flatMap { it.violations() }
        )
    }

    public fun validateBlocking(
        validity: Validity = Validity.default
    ): ValidationResult = runBlocking { validate(validity) }

    internal suspend fun built(): Validation = Validation(block).apply { block() }
}

public fun validation(
    block: suspend Validation.() -> Unit
): Validation = Validation(block)
