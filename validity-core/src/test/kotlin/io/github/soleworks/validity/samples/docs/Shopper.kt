package io.github.soleworks.validity.samples.docs

import java.math.BigDecimal

data class Shopper(
    val creditLimit: BigDecimal,
    val catalog: Set<String>
)
