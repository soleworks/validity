package io.github.soleworks.validity.phone

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType
import com.google.i18n.phonenumbers.PhoneNumberUtil.ValidationResult
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource

private const val UNKNOWN_REGION = "ZZ"
private const val PLUS = "+"
private const val MAX_AREA_CODE_LENGTH = 5

private val PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance()
private val CALLING_CODE_FORMAT = Regex("""[1-9]\d{0,2}""")
private val AREA_CODE_FORMAT = Regex("""\d{1,$MAX_AREA_CODE_LENGTH}""")
private val POSSIBLE_RESULTS = setOf(ValidationResult.IS_POSSIBLE, ValidationResult.IS_POSSIBLE_LOCAL_ONLY)
private val GEOGRAPHIC_TYPES = listOf(PhoneNumberType.FIXED_LINE, PhoneNumberType.MOBILE)
private val MOBILE_TYPES = setOf(PhoneNumberType.MOBILE, PhoneNumberType.FIXED_LINE_OR_MOBILE)

internal fun String.isPhone(
    countryCode: String?,
    areaCode: String?,
    format: PhoneFormat?
): Boolean = isPhoneNumber(
    countryCode = countryCode,
    areaCode = areaCode,
    format = format,
    types = null
)

internal fun String.isMobilePhone(
    countryCode: String?,
    areaCode: String?,
    format: PhoneFormat?
): Boolean = isPhoneNumber(
    countryCode = countryCode,
    areaCode = areaCode,
    format = format,
    types = MOBILE_TYPES
)

internal fun String.isCountryCode(): Boolean = toCallingCode() in PHONE_NUMBER_UTIL.supportedCallingCodes

internal fun String.isAreaCode(
    countryCode: String?
): Boolean = regions(countryCode).any { region -> isAreaCodeIn(region) }

private fun String.isPhoneNumber(
    countryCode: String?,
    areaCode: String?,
    format: PhoneFormat?,
    types: Set<PhoneNumberType>?
): Boolean {
    val formats = format?.let(::listOf) ?: PhoneFormat.entries

    return formats.any { candidate ->
        isPhoneNumberAs(
            format = candidate,
            countryCode = countryCode,
            areaCode = areaCode,
            types = types
        )
    }
}

private fun String.isPhoneNumberAs(
    format: PhoneFormat,
    countryCode: String?,
    areaCode: String?,
    types: Set<PhoneNumberType>?
): Boolean = when (format) {
    PhoneFormat.COMPLETE -> isComplete(countryCode = countryCode, areaCode = areaCode, types = types)

    PhoneFormat.AREA_CODE_AND_NUMBER -> regions(countryCode).any { region ->
        isValidIn(region = region, types = types, areaCode = areaCode)
    }

    PhoneFormat.NUMBER_ONLY -> regions(countryCode).any { region ->
        isNumberOnly(areaCode = areaCode, region = region, types = types)
    }
}

private fun String.isComplete(
    countryCode: String?,
    areaCode: String?,
    types: Set<PhoneNumberType>?
): Boolean {
    val text = if (trimStart().startsWith(PLUS)) this else PLUS + this
    val number = text.parse(UNKNOWN_REGION) ?: return false
    val isSameCountry = countryCode == null || number.countryCode == countryCode.toCallingCode()

    return PHONE_NUMBER_UTIL.isValidNumber(
        number
    ) &&
        isSameCountry &&
        number.hasType(types) &&
        number.hasAreaCode(areaCode)
}

private fun String.isNumberOnly(
    areaCode: String?,
    region: String,
    types: Set<PhoneNumberType>?
): Boolean {
    if (areaCode != null)
        return (areaCode + this).isValidIn(region = region, types = types)

    val number = parse(region) ?: return false

    return number.isFromRegion() && number.isPossible(types)
}

private fun String.isValidIn(
    region: String,
    types: Set<PhoneNumberType>? = null,
    areaCode: String? = null
): Boolean {
    val number = parse(region) ?: return false

    return number.isFromRegion() &&
        PHONE_NUMBER_UTIL.isValidNumberForRegion(number, region) &&
        number.hasType(types) &&
        number.hasAreaCode(areaCode)
}

private fun String.isAreaCodeIn(
    region: String
): Boolean {
    val examples = GEOGRAPHIC_TYPES
        .mapNotNull { type -> PHONE_NUMBER_UTIL.getExampleNumberForType(region, type) }
        .filter { example -> PHONE_NUMBER_UTIL.getLengthOfNationalDestinationCode(example) > 0 }

    if (examples.map(PHONE_NUMBER_UTIL::getLengthOfNationalDestinationCode).toSet().size > 1)
        return AREA_CODE_FORMAT.matches(this)

    return examples.any { example -> example.withAreaCode(areaCode = this, region = region) }
}

private fun PhoneNumber.withAreaCode(
    areaCode: String,
    region: String
): Boolean {
    val length = PHONE_NUMBER_UTIL.getLengthOfNationalDestinationCode(this)
    val subscriber = PHONE_NUMBER_UTIL.getNationalSignificantNumber(this).drop(length)

    return areaCode.length == length &&
        AREA_CODE_FORMAT.matches(areaCode) &&
        (areaCode + subscriber).isValidIn(region = region)
}

private fun regions(
    countryCode: String?
): Collection<String> = if (countryCode == null)
    PHONE_NUMBER_UTIL.supportedRegions
else
    countryCode.toCallingCode()?.let(PHONE_NUMBER_UTIL::getRegionCodesForCountryCode).orEmpty()

private fun String.parse(
    region: String
): PhoneNumber? = runCatching { PHONE_NUMBER_UTIL.parseAndKeepRawInput(this, region) }.getOrNull()

private fun PhoneNumber.isFromRegion(): Boolean = countryCodeSource == CountryCodeSource.FROM_DEFAULT_COUNTRY

private fun PhoneNumber.hasAreaCode(
    areaCode: String?
): Boolean {
    if (areaCode == null)
        return true

    val region = PHONE_NUMBER_UTIL.getRegionCodeForNumber(this) ?: return false
    val significantNumber = PHONE_NUMBER_UTIL.getNationalSignificantNumber(this)
    val subscriber = significantNumber.drop(PHONE_NUMBER_UTIL.getLengthOfNationalDestinationCode(this))
    val joined = (areaCode + subscriber).parse(region) ?: return false

    return joined.countryCode == countryCode &&
        PHONE_NUMBER_UTIL.getNationalSignificantNumber(
            joined
        ) == significantNumber
}

private fun PhoneNumber.hasType(
    types: Set<PhoneNumberType>?
): Boolean = types == null || PHONE_NUMBER_UTIL.getNumberType(this) in types

private fun PhoneNumber.isPossible(
    types: Set<PhoneNumberType>?
): Boolean = if (types == null)
    PHONE_NUMBER_UTIL.isPossibleNumberWithReason(this) in POSSIBLE_RESULTS
else
    types.any { type -> PHONE_NUMBER_UTIL.isPossibleNumberForTypeWithReason(this, type) in POSSIBLE_RESULTS }

private fun String.toCallingCode(): Int? = trim().removePrefix(PLUS).takeIf(CALLING_CODE_FORMAT::matches)?.toInt()
