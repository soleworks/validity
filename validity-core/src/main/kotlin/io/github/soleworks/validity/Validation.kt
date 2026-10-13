package io.github.soleworks.validity

@ValidityDsl
public class Validation internal constructor() {
    internal val nodes = mutableListOf<ValidationNode<*>>()

    internal var metadata: Map<String, Any?> = emptyMap()

    public fun add(
        node: ValidationNode<*>
    ) {
        nodes += node
    }

    public fun metadata(
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

    public fun validate(): ValidationResult = ValidationResult(
        violations = nodes.flatMap { it.validate() }
    )
}

public fun validation(
    block: Validation.() -> Unit
): Validation = Validation().apply(block)
