package io.github.soleworks.validity.phone

import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.MessagesKey
import io.github.soleworks.validity.ValidityDsl

@ValidityDsl
public class PhoneMessages internal constructor() {
    public var phone: String = "must be a valid phone number"
    public var mobilePhone: String = "must be a valid mobile phone number"
    public var countryCode: String = "must be a valid country calling code"
    public var areaCode: String = "must be a valid area code"
}

private val PHONE_MESSAGES = MessagesKey(::PhoneMessages)

internal val phoneMessages: PhoneMessages
    get() = PHONE_MESSAGES.current

public fun Messages.phone(
    block: PhoneMessages.() -> Unit
): Unit = module(PHONE_MESSAGES).block()
