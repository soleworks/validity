package io.github.soleworks.validity.samples

enum class PaymentMethod(
    private val label: String
) {
    PIX("Pix"),
    TED("TED"),
    BOLETO("Boleto");

    override fun toString(): String = label
}
