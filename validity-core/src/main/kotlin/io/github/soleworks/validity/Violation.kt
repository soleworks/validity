package io.github.soleworks.validity

import java.util.Objects

/**
 * One broken rule.
 *
 * ```text
 * Violation(path=items[1].quantity, message=must be at least 1, code=min)
 * ```
 *
 * @property path where the value is: a property (`email`), a nested one (`shipping.zipCode`), an element (`items[1]`)
 * or an entry (`prices[USD]`). It is empty for a rule about the whole object.
 * @property message the message for people, with its placeholders filled in. It follows the configured
 * [Messages].
 * @property code the stable name of the rule for programs, such as `minLength`. The built-in constraints use their
 * name, and a custom constraint the code it is given.
 * @property metadata the entries attached with `metadata`, empty when there are none.
 */
public class Violation(
    public val path: String,
    public val message: String,
    public val code: String,
    public val metadata: Map<String, Any?> = emptyMap()
) {
    override fun equals(
        other: Any?
    ): Boolean = other is Violation &&
        path == other.path &&
        message == other.message &&
        code == other.code &&
        metadata == other.metadata

    override fun hashCode(): Int = Objects.hash(path, message, code, metadata)

    override fun toString(): String = if (metadata.isEmpty())
        "Violation(path=$path, message=$message, code=$code)"
    else
        "Violation(path=$path, message=$message, code=$code, metadata=$metadata)"
}
