package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode

private val PLACEHOLDER_FORMAT = Regex("""\{(\w+)}""")

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

public fun ValidationNode<String>.countryCode(
    message: String = phoneMessages.countryCode
): Unit = constraint(
    message = message,
    code = "countryCode",
    predicate = { it.isCountryCode() }
)

public fun ValidationNode<String>.areaCode(
    countryCode: String? = null,
    message: String = phoneMessages.areaCode
): Unit = areaCode(
    countryCode = countryCode,
    message = { message.filled("countryCode" to countryCode) }
)

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
