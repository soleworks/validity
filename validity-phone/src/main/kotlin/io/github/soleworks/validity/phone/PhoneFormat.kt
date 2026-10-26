package io.github.soleworks.validity.phone

/**
 * How a phone number is written, for [phone], [mobilePhone] and their `format` parameter.
 */
public enum class PhoneFormat {
    /** With the country calling code, such as `+55 11 91234-5678`. The `+` is optional. */
    COMPLETE,

    /** With the area code but not the country calling code, such as `11 91234-5678`. */
    AREA_CODE_AND_NUMBER,

    /** Only the local number, such as `91234-5678`. */
    NUMBER_ONLY
}
