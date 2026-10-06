package io.github.soleworks.validity.phone

import io.github.soleworks.validity.ValidationNode

public fun ValidationNode<String>.phone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String = PhoneMessages.PHONE
): Unit = phone(
    countryCode = countryCode,
    areaCode = areaCode,
    format = format,
    message = { _, _, _ -> message }
)

public fun ValidationNode<String>.phone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String.(String?, String?, PhoneFormat?) -> String
): Unit = constraint(
    message = { it.message(countryCode, areaCode, format) },
    predicate = { PhoneNumbers.isValid(it, countryCode, areaCode, format) }
)

public fun ValidationNode<String>.mobilePhone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String = PhoneMessages.MOBILE_PHONE
): Unit = mobilePhone(
    countryCode = countryCode,
    areaCode = areaCode,
    format = format,
    message = { _, _, _ -> message }
)

public fun ValidationNode<String>.mobilePhone(
    countryCode: String? = null,
    areaCode: String? = null,
    format: PhoneFormat? = null,
    message: String.(String?, String?, PhoneFormat?) -> String
): Unit = constraint(
    message = { it.message(countryCode, areaCode, format) },
    predicate = { PhoneNumbers.isMobile(it, countryCode, areaCode, format) }
)

public fun ValidationNode<String>.countryCode(
    message: String = PhoneMessages.COUNTRY_CODE
): Unit = constraint(
    message = message,
    predicate = { PhoneNumbers.isCountryCode(it) }
)

public fun ValidationNode<String>.areaCode(
    countryCode: String? = null,
    message: String = PhoneMessages.AREA_CODE
): Unit = areaCode(
    countryCode = countryCode,
    message = { message.replace("{countryCode}", "$countryCode") }
)

public fun ValidationNode<String>.areaCode(
    countryCode: String? = null,
    message: String.(String?) -> String
): Unit = constraint(
    message = { it.message(countryCode) },
    predicate = { PhoneNumbers.isAreaCode(it, countryCode) }
)
