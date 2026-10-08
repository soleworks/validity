package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.messages
import java.math.BigInteger
import java.util.Currency

private const val IBAN_MODULUS = 97L
private const val IBAN_REMAINDER = 1
private const val LETTER_OFFSET = 10

private val CREDIT_CARD_FORMAT = Regex("\\d{12,19}")
private val BIC_FORMAT = Regex("[A-Z]{4}([A-Z]{2})[A-Z0-9]{2}(?:[A-Z0-9]{3})?")
private val ISIN_FORMAT = Regex("[A-Z]{2}[A-Z0-9]{9}\\d")
private val BITCOIN_ADDRESS_FORMAT = Regex("[13][A-HJ-NP-Za-km-z1-9]{25,39}|bc1[a-z0-9]{25,39}")
private val ETHEREUM_ADDRESS_FORMAT = Regex("0x[0-9a-fA-F]{40}")

private val CURRENCY_CODES = Currency.getAvailableCurrencies().map { it.currencyCode }.toSet()

private val IBAN_FORMATS: Map<String, Regex> = mapOf(
    "AD" to Regex("AD\\d{10}[A-Z0-9]{12}"),
    "AE" to Regex("AE\\d{21}"),
    "AL" to Regex("AL\\d{10}[A-Z0-9]{16}"),
    "AT" to Regex("AT\\d{18}"),
    "AX" to Regex("AX\\d{16}"),
    "AZ" to Regex("AZ\\d{2}[A-Z]{4}[A-Z0-9]{20}"),
    "BA" to Regex("BA\\d{18}"),
    "BE" to Regex("BE\\d{14}"),
    "BG" to Regex("BG\\d{2}[A-Z]{4}\\d{6}[A-Z0-9]{8}"),
    "BH" to Regex("BH\\d{2}[A-Z]{4}[A-Z0-9]{14}"),
    "BI" to Regex("BI\\d{25}"),
    "BL" to Regex("BL\\d{12}[A-Z0-9]{11}\\d{2}"),
    "BR" to Regex("BR\\d{25}[A-Z]{1}[A-Z0-9]{1}"),
    "BY" to Regex("BY\\d{2}[A-Z0-9]{4}\\d{4}[A-Z0-9]{16}"),
    "CH" to Regex("CH\\d{7}[A-Z0-9]{12}"),
    "CR" to Regex("CR\\d{20}"),
    "CY" to Regex("CY\\d{10}[A-Z0-9]{16}"),
    "CZ" to Regex("CZ\\d{22}"),
    "DE" to Regex("DE\\d{20}"),
    "DJ" to Regex("DJ\\d{25}"),
    "DK" to Regex("DK\\d{16}"),
    "DO" to Regex("DO\\d{2}[A-Z0-9]{4}\\d{20}"),
    "EE" to Regex("EE\\d{18}"),
    "EG" to Regex("EG\\d{27}"),
    "ES" to Regex("ES\\d{22}"),
    "FI" to Regex("FI\\d{16}"),
    "FK" to Regex("FK\\d{2}[A-Z]{2}\\d{12}"),
    "FO" to Regex("FO\\d{16}"),
    "FR" to Regex("FR\\d{12}[A-Z0-9]{11}\\d{2}"),
    "GB" to Regex("GB\\d{2}[A-Z]{4}\\d{14}"),
    "GE" to Regex("GE\\d{2}[A-Z]{2}\\d{16}"),
    "GF" to Regex("GF\\d{12}[A-Z0-9]{11}\\d{2}"),
    "GG" to Regex("GG\\d{2}[A-Z]{4}\\d{14}"),
    "GI" to Regex("GI\\d{2}[A-Z]{4}[A-Z0-9]{15}"),
    "GL" to Regex("GL\\d{16}"),
    "GP" to Regex("GP\\d{12}[A-Z0-9]{11}\\d{2}"),
    "GR" to Regex("GR\\d{9}[A-Z0-9]{16}"),
    "GT" to Regex("GT\\d{2}[A-Z0-9]{24}"),
    "HN" to Regex("HN\\d{2}[A-Z]{4}\\d{20}"),
    "HR" to Regex("HR\\d{19}"),
    "HU" to Regex("HU\\d{26}"),
    "IE" to Regex("IE\\d{2}[A-Z]{4}\\d{14}"),
    "IL" to Regex("IL\\d{21}"),
    "IM" to Regex("IM\\d{2}[A-Z]{4}\\d{14}"),
    "IQ" to Regex("IQ\\d{2}[A-Z]{4}\\d{15}"),
    "IS" to Regex("IS\\d{24}"),
    "IT" to Regex("IT\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}"),
    "JE" to Regex("JE\\d{2}[A-Z]{4}\\d{14}"),
    "JO" to Regex("JO\\d{2}[A-Z]{4}\\d{4}[A-Z0-9]{18}"),
    "KW" to Regex("KW\\d{2}[A-Z]{4}[A-Z0-9]{22}"),
    "KZ" to Regex("KZ\\d{5}[A-Z0-9]{13}"),
    "LB" to Regex("LB\\d{6}[A-Z0-9]{20}"),
    "LC" to Regex("LC\\d{2}[A-Z]{4}[A-Z0-9]{24}"),
    "LI" to Regex("LI\\d{7}[A-Z0-9]{12}"),
    "LT" to Regex("LT\\d{18}"),
    "LU" to Regex("LU\\d{5}[A-Z0-9]{13}"),
    "LV" to Regex("LV\\d{2}[A-Z]{4}[A-Z0-9]{13}"),
    "LY" to Regex("LY\\d{23}"),
    "MC" to Regex("MC\\d{12}[A-Z0-9]{11}\\d{2}"),
    "MD" to Regex("MD\\d{2}[A-Z0-9]{20}"),
    "ME" to Regex("ME\\d{20}"),
    "MF" to Regex("MF\\d{12}[A-Z0-9]{11}\\d{2}"),
    "MK" to Regex("MK\\d{5}[A-Z0-9]{10}\\d{2}"),
    "MN" to Regex("MN\\d{18}"),
    "MQ" to Regex("MQ\\d{12}[A-Z0-9]{11}\\d{2}"),
    "MR" to Regex("MR\\d{25}"),
    "MT" to Regex("MT\\d{2}[A-Z]{4}\\d{5}[A-Z0-9]{18}"),
    "MU" to Regex("MU\\d{2}[A-Z]{4}\\d{19}[A-Z]{3}"),
    "NC" to Regex("NC\\d{12}[A-Z0-9]{11}\\d{2}"),
    "NI" to Regex("NI\\d{2}[A-Z]{4}\\d{20}"),
    "NL" to Regex("NL\\d{2}[A-Z]{4}\\d{10}"),
    "NO" to Regex("NO\\d{13}"),
    "OM" to Regex("OM\\d{5}[A-Z0-9]{16}"),
    "PF" to Regex("PF\\d{12}[A-Z0-9]{11}\\d{2}"),
    "PK" to Regex("PK\\d{2}[A-Z]{4}[A-Z0-9]{16}"),
    "PL" to Regex("PL\\d{26}"),
    "PM" to Regex("PM\\d{12}[A-Z0-9]{11}\\d{2}"),
    "PS" to Regex("PS\\d{2}[A-Z]{4}[A-Z0-9]{21}"),
    "PT" to Regex("PT\\d{23}"),
    "QA" to Regex("QA\\d{2}[A-Z]{4}[A-Z0-9]{21}"),
    "RE" to Regex("RE\\d{12}[A-Z0-9]{11}\\d{2}"),
    "RO" to Regex("RO\\d{2}[A-Z]{4}[A-Z0-9]{16}"),
    "RS" to Regex("RS\\d{20}"),
    "RU" to Regex("RU\\d{16}[A-Z0-9]{15}"),
    "SA" to Regex("SA\\d{4}[A-Z0-9]{18}"),
    "SC" to Regex("SC\\d{2}[A-Z]{4}\\d{20}[A-Z]{3}"),
    "SD" to Regex("SD\\d{16}"),
    "SE" to Regex("SE\\d{22}"),
    "SI" to Regex("SI\\d{17}"),
    "SK" to Regex("SK\\d{22}"),
    "SM" to Regex("SM\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}"),
    "SO" to Regex("SO\\d{21}"),
    "ST" to Regex("ST\\d{23}"),
    "SV" to Regex("SV\\d{2}[A-Z]{4}\\d{20}"),
    "TF" to Regex("TF\\d{12}[A-Z0-9]{11}\\d{2}"),
    "TL" to Regex("TL\\d{21}"),
    "TN" to Regex("TN\\d{22}"),
    "TR" to Regex("TR\\d{8}[A-Z0-9]{16}"),
    "UA" to Regex("UA\\d{8}[A-Z0-9]{19}"),
    "VA" to Regex("VA\\d{20}"),
    "VG" to Regex("VG\\d{2}[A-Z]{4}\\d{16}"),
    "WF" to Regex("WF\\d{12}[A-Z0-9]{11}\\d{2}"),
    "XK" to Regex("XK\\d{18}"),
    "YE" to Regex("YE\\d{2}[A-Z]{4}\\d{4}[A-Z0-9]{18}"),
    "YT" to Regex("YT\\d{12}[A-Z0-9]{11}\\d{2}")
)

public fun ValidationNode<String>.creditCard(
    message: String = messages.creditCard
): Unit = constraint(
    message = message,
    predicate = { CREDIT_CARD_FORMAT.matches(it) && it.hasLuhnCheckDigit() }
)

public fun ValidationNode<String>.iban(
    message: String = messages.iban
): Unit = constraint(
    message = message,
    predicate = { IBAN_FORMATS[it.take(2)]?.matches(it) == true && it.hasIbanCheckDigits() }
)

public fun ValidationNode<String>.bic(
    message: String = messages.bic
): Unit = constraint(
    message = message,
    predicate = { BIC_FORMAT.matchEntire(it)?.groupValues?.get(1) in COUNTRY_CODES }
)

public fun ValidationNode<String>.isin(
    message: String = messages.isin
): Unit = constraint(
    message = message,
    predicate = { ISIN_FORMAT.matches(it) && it.toDigitString().hasLuhnCheckDigit() }
)

public fun ValidationNode<String>.currencyCode(
    message: String = messages.currencyCode
): Unit = constraint(
    message = message,
    predicate = { it in CURRENCY_CODES }
)

public fun ValidationNode<String>.bitcoinAddress(
    message: String = messages.bitcoinAddress
): Unit = constraint(
    message = message,
    predicate = { BITCOIN_ADDRESS_FORMAT.matches(it) }
)

public fun ValidationNode<String>.ethereumAddress(
    message: String = messages.ethereumAddress
): Unit = constraint(
    message = message,
    predicate = { ETHEREUM_ADDRESS_FORMAT.matches(it) }
)

private fun String.toDigitString(): String = map(Char::toDigitValue).joinToString("")

private fun Char.toDigitValue(): Int = if (isLetter()) this - 'A' + LETTER_OFFSET else digitToInt()

private fun String.hasIbanCheckDigits(): Boolean = BigInteger((drop(4) + take(4)).toDigitString())
    .mod(BigInteger.valueOf(IBAN_MODULUS))
    .toInt() == IBAN_REMAINDER
