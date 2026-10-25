package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages
import java.math.BigDecimal
import java.time.ZoneId
import java.util.Locale

private const val MAX_LATITUDE = 90.0
private const val MAX_LONGITUDE = 180.0

internal val COUNTRY_CODES: Set<String> = Locale.getISOCountries().toSet()
private val COUNTRY_CODES_ALPHA3 = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3)
private val WITHDRAWN_LANGUAGE_CODES = setOf("in", "iw", "ji", "mo")
private val LANGUAGE_CODES = Locale.getISOLanguages().toSet() - WITHDRAWN_LANGUAGE_CODES
private val TIME_ZONES = ZoneId.getAvailableZoneIds()
private val LATITUDES = -MAX_LATITUDE..MAX_LATITUDE
private val LONGITUDES = -MAX_LONGITUDE..MAX_LONGITUDE
private val DECIMAL_LATITUDES = BigDecimal.valueOf(-MAX_LATITUDE)..BigDecimal.valueOf(MAX_LATITUDE)
private val DECIMAL_LONGITUDES = BigDecimal.valueOf(-MAX_LONGITUDE)..BigDecimal.valueOf(MAX_LONGITUDE)
private val COORDINATES_FORMAT = Regex("""([+-]?\d+(?:\.\d+)?)\s*,\s*([+-]?\d+(?:\.\d+)?)""")
private val THREE_DIGITS_FORMAT = Regex("""\d{3}""")
private val FOUR_DIGITS_FORMAT = Regex("""\d{4}""")
private val FIVE_DIGITS_FORMAT = Regex("""\d{5}""")
private val SIX_DIGITS_FORMAT = Regex("""\d{6}""")
private val POSTAL_CODES: Map<String, Regex> = mapOf(
    "AD" to Regex("""AD\d{3}"""),
    "AR" to Regex("""([A-HJ-NP-Z]\d{4}[A-Z]{3}|[1-9]\d{3})""", RegexOption.IGNORE_CASE),
    "AT" to FOUR_DIGITS_FORMAT,
    "AU" to FOUR_DIGITS_FORMAT,
    "AZ" to Regex("""AZ\d{4}"""),
    "BA" to Regex("""([7-8]\d{4})"""),
    "BD" to Regex("""([1-8][0-9]{3}|9[0-4][0-9]{2})"""),
    "BE" to FOUR_DIGITS_FORMAT,
    "BG" to FOUR_DIGITS_FORMAT,
    "BR" to Regex("""\d{5}-?\d{3}"""),
    "BY" to Regex("""2[1-4]\d{4}"""),
    "CA" to Regex(
        """[ABCEGHJKLMNPRSTVXY]\d[ABCEGHJ-NPRSTV-Z][\s\-]?\d[ABCEGHJ-NPRSTV-Z]\d""",
        RegexOption.IGNORE_CASE
    ),
    "CH" to FOUR_DIGITS_FORMAT,
    "CN" to Regex("""(0[1-7]|1[012356]|2[0-7]|3[0-6]|4[0-7]|5[1-7]|6[1-7]|7[1-5]|8[1345]|9[09])\d{4}"""),
    "CO" to Regex(
        """(05|08|11|13|15|17|18|19|20|23|25|27|41|44|47|50|52|54|63|66|68|70|73|76|81|85|86|88|91|94|95|97|99)\d{4}"""
    ),
    "CZ" to Regex("""\d{3}\s?\d{2}"""),
    "DE" to FIVE_DIGITS_FORMAT,
    "DK" to FOUR_DIGITS_FORMAT,
    "DO" to FIVE_DIGITS_FORMAT,
    "DZ" to FIVE_DIGITS_FORMAT,
    "EE" to FIVE_DIGITS_FORMAT,
    "ES" to Regex("""(5[0-2]{1}|[0-4]{1}\d{1})\d{3}"""),
    "FI" to FIVE_DIGITS_FORMAT,
    "FR" to Regex("""(?:(?:0[1-9]|[1-8]\d|9[0-5])\d{3}|97[1-46]\d{2})"""),
    "GB" to Regex("""(gir\s?0aa|[a-z]{1,2}\d[\da-z]?\s?(\d[a-z]{2})?)""", RegexOption.IGNORE_CASE),
    "GR" to Regex("""\d{3}\s?\d{2}"""),
    "HR" to Regex("""([1-5]\d{4})"""),
    "HT" to Regex("""HT\d{4}"""),
    "HU" to FOUR_DIGITS_FORMAT,
    "ID" to FIVE_DIGITS_FORMAT,
    "IE" to Regex("""(?!.*(?:o))[A-Za-z]\d[\dw]\s\w{4}""", RegexOption.IGNORE_CASE),
    "IL" to Regex("""(\d{5}|\d{7})"""),
    "IN" to Regex("""((?!10|29|35|54|55|65|66|86|87|88|89)[1-9][0-9]{5})"""),
    "IR" to Regex("""(?!(\d)\1{3})[13-9]{4}[1346-9][013-9]{5}"""),
    "IS" to THREE_DIGITS_FORMAT,
    "IT" to FIVE_DIGITS_FORMAT,
    "JO" to FIVE_DIGITS_FORMAT,
    "JP" to Regex("""\d{3}\-\d{4}"""),
    "KE" to FIVE_DIGITS_FORMAT,
    "KR" to Regex("""(\d{5}|\d{6})"""),
    "LI" to Regex("""(948[5-9]|949[0-7])"""),
    "LT" to Regex("""LT\-\d{5}"""),
    "LU" to FOUR_DIGITS_FORMAT,
    "LV" to Regex("""LV\-\d{4}"""),
    "LK" to FIVE_DIGITS_FORMAT,
    "MC" to Regex("""980\d{2}"""),
    "MG" to THREE_DIGITS_FORMAT,
    "MX" to FIVE_DIGITS_FORMAT,
    "MT" to Regex("""[A-Za-z]{3}\s{0,1}\d{4}"""),
    "MY" to FIVE_DIGITS_FORMAT,
    "NL" to Regex("""[1-9]\d{3}\s?(?!sa|sd|ss)[a-z]{2}""", RegexOption.IGNORE_CASE),
    "NO" to FOUR_DIGITS_FORMAT,
    "NP" to Regex("""(10|21|22|32|33|34|44|45|56|57)\d{3}|977""", RegexOption.IGNORE_CASE),
    "NZ" to FOUR_DIGITS_FORMAT,
    "PK" to FIVE_DIGITS_FORMAT,
    "PL" to Regex("""\d{2}\-\d{3}"""),
    "PR" to Regex("""00[679]\d{2}([ -]\d{4})?"""),
    "PT" to Regex("""\d{4}\-\d{3}?"""),
    "RO" to SIX_DIGITS_FORMAT,
    "RU" to SIX_DIGITS_FORMAT,
    "SA" to FIVE_DIGITS_FORMAT,
    "SE" to Regex("""[1-9]\d{2}\s?\d{2}"""),
    "SG" to SIX_DIGITS_FORMAT,
    "SI" to FOUR_DIGITS_FORMAT,
    "SK" to Regex("""\d{3}\s?\d{2}"""),
    "TH" to FIVE_DIGITS_FORMAT,
    "TN" to FOUR_DIGITS_FORMAT,
    "TW" to Regex("""\d{3}(\d{2,3})?"""),
    "UA" to FIVE_DIGITS_FORMAT,
    "US" to Regex("""\d{5}(-\d{4})?"""),
    "ZA" to FOUR_DIGITS_FORMAT,
    "ZM" to FIVE_DIGITS_FORMAT
)

public fun ValidationNode<String>.isoCountryCode(
    message: String = messages.isoCountryCode
): Unit = constraint(
    message = message,
    code = "isoCountryCode",
    predicate = { it in COUNTRY_CODES }
)

public fun ValidationNode<String>.isoCountryCodeAlpha3(
    message: String = messages.isoCountryCodeAlpha3
): Unit = constraint(
    message = message,
    code = "isoCountryCodeAlpha3",
    predicate = { it in COUNTRY_CODES_ALPHA3 }
)

public fun ValidationNode<String>.languageCode(
    message: String = messages.languageCode
): Unit = constraint(
    message = message,
    code = "languageCode",
    predicate = { it in LANGUAGE_CODES }
)

public fun ValidationNode<String>.locale(
    message: String = messages.locale
): Unit = constraint(
    message = message,
    code = "locale",
    predicate = { it.isNotEmpty() && runCatching { Locale.Builder().setLanguageTag(it).build() }.isSuccess }
)

public fun ValidationNode<String>.timeZone(
    message: String = messages.timeZone
): Unit = constraint(
    message = message,
    code = "timeZone",
    predicate = { it in TIME_ZONES }
)

public fun ValidationNode<Double>.latitude(
    message: String = messages.latitude
): Unit = constraint(
    message = message,
    code = "latitude",
    predicate = { it in LATITUDES }
)

@JvmName("latitudeFloat")
public fun ValidationNode<Float>.latitude(
    message: String = messages.latitude
): Unit = constraint(
    message = message,
    code = "latitude",
    predicate = { it.toDouble() in LATITUDES }
)

@JvmName("latitudeBigDecimal")
public fun ValidationNode<BigDecimal>.latitude(
    message: String = messages.latitude
): Unit = constraint(
    message = message,
    code = "latitude",
    predicate = { it in DECIMAL_LATITUDES }
)

@JvmName("latitudeString")
public fun ValidationNode<String>.latitude(
    message: String = messages.latitude
): Unit = constraint(
    message = message,
    code = "latitude",
    predicate = { NUMERIC_FORMAT.matches(it) && it.toBigDecimal() in DECIMAL_LATITUDES }
)

public fun ValidationNode<Double>.longitude(
    message: String = messages.longitude
): Unit = constraint(
    message = message,
    code = "longitude",
    predicate = { it in LONGITUDES }
)

@JvmName("longitudeFloat")
public fun ValidationNode<Float>.longitude(
    message: String = messages.longitude
): Unit = constraint(
    message = message,
    code = "longitude",
    predicate = { it.toDouble() in LONGITUDES }
)

@JvmName("longitudeBigDecimal")
public fun ValidationNode<BigDecimal>.longitude(
    message: String = messages.longitude
): Unit = constraint(
    message = message,
    code = "longitude",
    predicate = { it in DECIMAL_LONGITUDES }
)

@JvmName("longitudeString")
public fun ValidationNode<String>.longitude(
    message: String = messages.longitude
): Unit = constraint(
    message = message,
    code = "longitude",
    predicate = { NUMERIC_FORMAT.matches(it) && it.toBigDecimal() in DECIMAL_LONGITUDES }
)

public fun ValidationNode<String>.coordinates(
    message: String = messages.coordinates
): Unit = constraint(
    message = message,
    code = "coordinates",
    predicate = { it.isCoordinates() }
)

public fun ValidationNode<String>.postalCode(
    country: String?,
    message: String = messages.postalCode
): Unit = postalCode(
    country = country,
    message = { message.filled("country" to country) }
)

public fun ValidationNode<String>.postalCode(
    country: String?,
    message: String.(String) -> String
) {
    val format = country?.let { POSTAL_CODES[it.uppercase()] }

    if (country == null || format == null)
        return

    constraint(
        message = { it.message(country) },
        code = "postalCode",
        predicate = { format.matches(it) }
    )
}

private fun String.isCoordinates(): Boolean {
    val (latitude, longitude) = COORDINATES_FORMAT.groups(this) ?: return false

    return latitude.toBigDecimal() in DECIMAL_LATITUDES && longitude.toBigDecimal() in DECIMAL_LONGITUDES
}
