package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.FormatMessages
import io.github.soleworks.validity.ValidationNode
import java.math.BigDecimal
import java.time.ZoneId
import java.util.Locale

private const val MAX_LATITUDE = 90.0
private const val MAX_LONGITUDE = 180.0

internal val COUNTRY_CODES: Set<String> = Locale.getISOCountries().toSet()
private val COUNTRY_CODES_ALPHA3 = Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA3)
private val LANGUAGE_CODES = Locale.getISOLanguages().toSet()
private val TIME_ZONES = ZoneId.getAvailableZoneIds()
private val LATITUDES = BigDecimal.valueOf(-MAX_LATITUDE)..BigDecimal.valueOf(MAX_LATITUDE)
private val LONGITUDES = BigDecimal.valueOf(-MAX_LONGITUDE)..BigDecimal.valueOf(MAX_LONGITUDE)
private val DECIMAL_FORMAT = Regex("[+-]?\\d+(?:\\.\\d+)?")

public fun ValidationNode<String>.isoCountryCode(
    message: String = FormatMessages.ISO_COUNTRY_CODE
): Unit = constraint(
    message = message,
    predicate = { it in COUNTRY_CODES }
)

public fun ValidationNode<String>.isoCountryCodeAlpha3(
    message: String = FormatMessages.ISO_COUNTRY_CODE_ALPHA3
): Unit = constraint(
    message = message,
    predicate = { it in COUNTRY_CODES_ALPHA3 }
)

public fun ValidationNode<String>.languageCode(
    message: String = FormatMessages.LANGUAGE_CODE
): Unit = constraint(
    message = message,
    predicate = { it in LANGUAGE_CODES }
)

public fun ValidationNode<String>.locale(
    message: String = FormatMessages.LOCALE
): Unit = constraint(
    message = message,
    predicate = { it.isNotEmpty() && runCatching { Locale.Builder().setLanguageTag(it).build() }.isSuccess }
)

public fun ValidationNode<String>.timeZone(
    message: String = FormatMessages.TIME_ZONE
): Unit = constraint(
    message = message,
    predicate = { it in TIME_ZONES }
)

public fun ValidationNode<Double>.latitude(
    message: String = FormatMessages.LATITUDE
): Unit = constraint(
    message = message,
    predicate = { it in -MAX_LATITUDE..MAX_LATITUDE }
)

@JvmName("latitudeFloat")
public fun ValidationNode<Float>.latitude(
    message: String = FormatMessages.LATITUDE
): Unit = constraint(
    message = message,
    predicate = { it.toDouble() in -MAX_LATITUDE..MAX_LATITUDE }
)

@JvmName("latitudeBigDecimal")
public fun ValidationNode<BigDecimal>.latitude(
    message: String = FormatMessages.LATITUDE
): Unit = constraint(
    message = message,
    predicate = { it in LATITUDES }
)

@JvmName("latitudeString")
public fun ValidationNode<String>.latitude(
    message: String = FormatMessages.LATITUDE
): Unit = constraint(
    message = message,
    predicate = { DECIMAL_FORMAT.matches(it) && BigDecimal(it) in LATITUDES }
)

public fun ValidationNode<Double>.longitude(
    message: String = FormatMessages.LONGITUDE
): Unit = constraint(
    message = message,
    predicate = { it in -MAX_LONGITUDE..MAX_LONGITUDE }
)

@JvmName("longitudeFloat")
public fun ValidationNode<Float>.longitude(
    message: String = FormatMessages.LONGITUDE
): Unit = constraint(
    message = message,
    predicate = { it.toDouble() in -MAX_LONGITUDE..MAX_LONGITUDE }
)

@JvmName("longitudeBigDecimal")
public fun ValidationNode<BigDecimal>.longitude(
    message: String = FormatMessages.LONGITUDE
): Unit = constraint(
    message = message,
    predicate = { it in LONGITUDES }
)

@JvmName("longitudeString")
public fun ValidationNode<String>.longitude(
    message: String = FormatMessages.LONGITUDE
): Unit = constraint(
    message = message,
    predicate = { DECIMAL_FORMAT.matches(it) && BigDecimal(it) in LONGITUDES }
)
