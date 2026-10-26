package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode

private val PLACEHOLDER_FORMAT = Regex("""\{(\w+)}""")

/**
 * Checks that the string is a valid phone number, by the metadata of libphonenumber.
 *
 * The arguments narrow what passes: a number from another country, with another area code or written in another
 * [PhoneFormat] fails.
 *
 * Fails with code `phone` and, by default, the message `must be a valid phone number`
 * ([PhoneMessages.phone][io.github.soleworks.validity.phone.PhoneMessages.phone]).
 *
 * ```kotlin
 * ::phone required { phone(countryCode = "55", format = PhoneFormat.COMPLETE) }
 * ```
 *
 * @param countryCode the country calling code the number must have, such as `55` or `+55`. With `null`, any country.
 * @param areaCode the area code the number must have, such as `11`. With `null`, any area code.
 * @param format how the number is written. With `null`, any of the [PhoneFormat] entries.
 * @param message the message of the violation. `{countryCode}`, `{areaCode}`, `{format}`, `{path}` and `{value}` are
 *     filled in.
 */
public fun ValidationNode<String>.phone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String = phoneMessages.phone
): Unit = phone(
    countryCode = countryCode,
    areaCode = areaCode,
    format = format,
    message = { _, _, _ -> message.filled("countryCode" to countryCode, "areaCode" to areaCode, "format" to format) }
)

/**
 * Checks that the string is a valid phone number, by the metadata of libphonenumber.
 *
 * The arguments narrow what passes: a number from another country, with another area code or written in another
 * [PhoneFormat] fails.
 *
 * Fails with code `phone` and the message that [message] builds.
 *
 * @param countryCode the country calling code the number must have, such as `55` or `+55`. With `null`, any country.
 * @param areaCode the area code the number must have, such as `11`. With `null`, any area code.
 * @param format how the number is written. With `null`, any of the [PhoneFormat] entries.
 * @param message builds the message from the value (the receiver), [countryCode], [areaCode] and [format].
 */
public fun ValidationNode<String>.phone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String.(String?, String?, PhoneFormat?) -> String
): Unit = constraint(
    message = { it.message(countryCode, areaCode, format) },
    code = "phone",
    predicate = { it.isPhone(countryCode = countryCode, areaCode = areaCode, format = format) }
)

/**
 * Checks that the string is a valid mobile phone number, by the metadata of libphonenumber.
 *
 * The arguments narrow what passes: a number from another country, with another area code or written in another
 * [PhoneFormat] fails.
 *
 * Numbers that can be mobile or fixed line, as in the United States, pass.
 *
 * Fails with code `mobilePhone` and, by default, the message `must be a valid mobile phone number`
 * ([PhoneMessages.mobilePhone][io.github.soleworks.validity.phone.PhoneMessages.mobilePhone]).
 *
 * ```kotlin
 * ::mobile required { mobilePhone(countryCode = "55") }
 * ```
 *
 * @param countryCode the country calling code the number must have, such as `55` or `+55`. With `null`, any country.
 * @param areaCode the area code the number must have, such as `11`. With `null`, any area code.
 * @param format how the number is written. With `null`, any of the [PhoneFormat] entries.
 * @param message the message of the violation. `{countryCode}`, `{areaCode}`, `{format}`, `{path}` and `{value}` are
 *     filled in.
 */
public fun ValidationNode<String>.mobilePhone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String = phoneMessages.mobilePhone
): Unit = mobilePhone(
    countryCode = countryCode,
    areaCode = areaCode,
    format = format,
    message = { _, _, _ -> message.filled("countryCode" to countryCode, "areaCode" to areaCode, "format" to format) }
)

/**
 * Checks that the string is a valid mobile phone number, by the metadata of libphonenumber.
 *
 * The arguments narrow what passes: a number from another country, with another area code or written in another
 * [PhoneFormat] fails.
 *
 * Numbers that can be mobile or fixed line, as in the United States, pass.
 *
 * Fails with code `mobilePhone` and the message that [message] builds.
 *
 * @param countryCode the country calling code the number must have, such as `55` or `+55`. With `null`, any country.
 * @param areaCode the area code the number must have, such as `11`. With `null`, any area code.
 * @param format how the number is written. With `null`, any of the [PhoneFormat] entries.
 * @param message builds the message from the value (the receiver), [countryCode], [areaCode] and [format].
 */
public fun ValidationNode<String>.mobilePhone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String.(String?, String?, PhoneFormat?) -> String
): Unit = constraint(
    message = { it.message(countryCode, areaCode, format) },
    code = "mobilePhone",
    predicate = { it.isMobilePhone(countryCode = countryCode, areaCode = areaCode, format = format) }
)

/**
 * Checks that the string is a country calling code, such as `55` or `+1`, with or without the `+`.
 *
 * Fails with code `countryCode` and, by default, the message `must be a valid country calling code`
 * ([PhoneMessages.countryCode][io.github.soleworks.validity.phone.PhoneMessages.countryCode]).
 *
 * @param message the message of the violation. `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.countryCode(
    message: String = phoneMessages.countryCode
): Unit = constraint(
    message = message,
    code = "countryCode",
    predicate = { it.isCountryCode() }
)

/**
 * Checks that the string is an area code used in [countryCode], such as `11` in Brazil.
 *
 * Where the area codes of a country have different lengths, any 1 to 5 digits pass.
 *
 * Fails with code `areaCode` and, by default, the message `must be a valid area code`
 * ([PhoneMessages.areaCode][io.github.soleworks.validity.phone.PhoneMessages.areaCode]).
 *
 * @param countryCode the country calling code, such as `55`. With `null`, an area code of any country passes.
 * @param message the message of the violation. `{countryCode}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.areaCode(
    countryCode: String? = null,
    message: String = phoneMessages.areaCode
): Unit = areaCode(
    countryCode = countryCode,
    message = { message.filled("countryCode" to countryCode) }
)

/**
 * Checks that the string is an area code used in [countryCode], such as `11` in Brazil.
 *
 * Where the area codes of a country have different lengths, any 1 to 5 digits pass.
 *
 * Fails with code `areaCode` and the message that [message] builds.
 *
 * @param countryCode the country calling code, such as `55`. With `null`, an area code of any country passes.
 * @param message builds the message from the value (the receiver) and [countryCode].
 */
public fun ValidationNode<String>.areaCode(
    countryCode: String? = null,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(countryCode) },
    code = "areaCode",
    predicate = { it.isAreaCode(countryCode) }
)

private fun String.filled(
    vararg placeholders: Pair<String, Any?>
): String {
    val values = placeholders.toMap()

    return PLACEHOLDER_FORMAT.replace(this) { match ->
        val name = match.groupValues[1]

        if (name in values)
            values[name].placeholderText()
        else
            match.value
    }
}

private fun Any?.placeholderText(): String = when (this) {
    null -> ""
    is Iterable<*> -> joinToString()
    else -> toString()
}
