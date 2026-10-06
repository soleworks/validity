package io.github.soleworks.validity.phone

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType
import com.google.i18n.phonenumbers.PhoneNumberUtil.ValidationResult
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource

internal object PhoneNumbers {
    private const val UNKNOWN_REGION = "ZZ"
    private const val PLUS = "+"
    private const val MAX_AREA_CODE_LENGTH = 5

    private val util = PhoneNumberUtil.getInstance()
    private val possible = setOf(ValidationResult.IS_POSSIBLE, ValidationResult.IS_POSSIBLE_LOCAL_ONLY)
    private val geographicTypes = listOf(PhoneNumberType.FIXED_LINE, PhoneNumberType.MOBILE)
    private val mobileTypes = setOf(PhoneNumberType.MOBILE, PhoneNumberType.FIXED_LINE_OR_MOBILE)

    fun isValid(
        text: String,
        countryCode: String?,
        areaCode: String?,
        format: PhoneFormat?
    ): Boolean = matches(text, countryCode, areaCode, format, null)

    fun isMobile(
        text: String,
        countryCode: String?,
        areaCode: String?,
        format: PhoneFormat?
    ): Boolean = matches(text, countryCode, areaCode, format, mobileTypes)

    fun isCountryCode(text: String): Boolean = text.toCallingCode() in util.supportedCallingCodes

    fun isAreaCode(
        text: String,
        countryCode: String?
    ): Boolean = regions(countryCode).any { region -> isAreaCodeIn(text, region) }

    private fun matches(
        text: String,
        countryCode: String?,
        areaCode: String?,
        format: PhoneFormat?,
        types: Set<PhoneNumberType>?
    ): Boolean {
        val formats = format?.let(::listOf) ?: PhoneFormat.entries

        return formats.any { candidate -> matchesAs(candidate, text, countryCode, areaCode, types) }
    }

    private fun matchesAs(
        format: PhoneFormat,
        text: String,
        countryCode: String?,
        areaCode: String?,
        types: Set<PhoneNumberType>?
    ): Boolean = when (format) {
        PhoneFormat.COMPLETE -> isComplete(text, countryCode, types)
        PhoneFormat.AREA_CODE_AND_NUMBER -> regions(countryCode).any { region -> isValidIn(text, region, types) }
        PhoneFormat.NUMBER_ONLY -> regions(countryCode).any { region -> isNumberOnly(text, areaCode, region, types) }
    }

    private fun isComplete(
        text: String,
        countryCode: String?,
        types: Set<PhoneNumberType>?
    ): Boolean {
        val number = parse(if (text.trimStart().startsWith(PLUS)) text else PLUS + text, UNKNOWN_REGION) ?: return false
        val sameCountry = countryCode == null || number.countryCode == countryCode.toCallingCode()

        return util.isValidNumber(number) && sameCountry && number.hasType(types)
    }

    private fun isNumberOnly(
        text: String,
        areaCode: String?,
        region: String,
        types: Set<PhoneNumberType>?
    ): Boolean {
        if (areaCode != null)
            return isValidIn(areaCode + text, region, types)

        val number = parse(text, region) ?: return false

        return number.isFromRegion() && number.isPossible(types)
    }

    private fun isValidIn(
        text: String,
        region: String,
        types: Set<PhoneNumberType>? = null
    ): Boolean {
        val number = parse(text, region) ?: return false

        return number.isFromRegion() && util.isValidNumberForRegion(number, region) && number.hasType(types)
    }

    private fun isAreaCodeIn(
        text: String,
        region: String
    ): Boolean {
        val examples = geographicTypes
            .mapNotNull { type -> util.getExampleNumberForType(region, type) }
            .filter { example -> util.getLengthOfNationalDestinationCode(example) > 0 }

        if (examples.map(util::getLengthOfNationalDestinationCode).toSet().size > 1)
            return text.length in 1..MAX_AREA_CODE_LENGTH && text.all(Char::isDigit)

        return examples.any { example -> example.withAreaCode(text, region) }
    }

    private fun PhoneNumber.withAreaCode(
        areaCode: String,
        region: String
    ): Boolean {
        val length = util.getLengthOfNationalDestinationCode(this)
        val subscriber = util.getNationalSignificantNumber(this).drop(length)

        return areaCode.length == length && areaCode.all(Char::isDigit) && isValidIn(areaCode + subscriber, region)
    }

    private fun regions(countryCode: String?): Collection<String> = if (countryCode == null)
        util.supportedRegions
    else
        countryCode.toCallingCode()?.let(util::getRegionCodesForCountryCode).orEmpty()

    private fun parse(
        text: String,
        region: String
    ): PhoneNumber? = runCatching { util.parseAndKeepRawInput(text, region) }.getOrNull()

    private fun PhoneNumber.isFromRegion(): Boolean = countryCodeSource == CountryCodeSource.FROM_DEFAULT_COUNTRY

    private fun PhoneNumber.hasType(types: Set<PhoneNumberType>?): Boolean =
        types == null || util.getNumberType(this) in types

    private fun PhoneNumber.isPossible(types: Set<PhoneNumberType>?): Boolean = if (types == null)
        util.isPossibleNumberWithReason(this) in possible
    else
        types.any { type -> util.isPossibleNumberForTypeWithReason(this, type) in possible }

    private fun String.toCallingCode(): Int? = trim().removePrefix(PLUS).toIntOrNull()
}
