package io.github.soleworks.validity

@ValidityDsl
public class Validation internal constructor() {
    internal val nodes = mutableListOf<ValidationNode<*>>()

    public fun add(node: ValidationNode<*>) {
        nodes += node
    }

    internal fun validate(): List<Violation> = nodes.flatMap { it.validate() }
}

public fun validation(block: Validation.() -> Unit): Validation = Validation().apply(block)
