package io.github.soleworks.validity

@ValidityDsl
public class Validation internal constructor() {
    internal val nodes = mutableListOf<ValidationNode<*>>()

    internal var metadata: Any? = null

    public fun add(
        node: ValidationNode<*>
    ) {
        nodes += node
    }

    public fun metadata(
        metadata: Any?,
        block: Validation.() -> Unit
    ) {
        val outer = this.metadata

        this.metadata = metadata

        try {
            block()
        } finally {
            this.metadata = outer
        }
    }

    public fun validate(): ValidationResult = ValidationResult(
        violations = nodes.flatMap { it.validate() }
    )
}

public fun validation(
    block: Validation.() -> Unit
): Validation = Validation().apply(block)
