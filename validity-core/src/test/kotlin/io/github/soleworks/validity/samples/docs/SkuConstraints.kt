package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.ValidationNode

private val SKU_FORMAT = Regex("""[A-Z]{3}-\d{4}""")

fun ValidationNode<String>.sku(
    message: String = "must be a SKU such as ABC-1234"
) {
    constraint(message = message, code = "sku") { SKU_FORMAT.matches(it) }
}
