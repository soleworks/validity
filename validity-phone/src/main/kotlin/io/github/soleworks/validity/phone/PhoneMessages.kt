package io.github.soleworks.validity.phone

import io.github.soleworks.validity.Language
import io.github.soleworks.validity.Messages
import io.github.soleworks.validity.MessagesKey
import io.github.soleworks.validity.ValidityDsl

/**
 * The default messages of the phone constraints.
 *
 * Change them with [Messages.phone][io.github.soleworks.validity.phone.phone].
 */
@ValidityDsl
public class PhoneMessages internal constructor() {
    /** Default message of `phone`. */
    public var phone: String = "must be a valid phone number"

    /** Default message of `mobilePhone`. */
    public var mobilePhone: String = "must be a valid mobile phone number"

    /** Default message of `countryCode`. */
    public var countryCode: String = "must be a valid country calling code"

    /** Default message of `areaCode`. */
    public var areaCode: String = "must be a valid area code"
}

private val PHONE_MESSAGES = MessagesKey(
    create = ::PhoneMessages,
    translations = mapOf(
        Language.ES to {
            phone = "debe ser un número de teléfono válido"
            mobilePhone = "debe ser un número de teléfono móvil válido"
            countryCode = "debe ser un código telefónico de país válido"
            areaCode = "debe ser un código de área válido"
        },
        Language.PT_BR to {
            phone = "deve ser um número de telefone válido"
            mobilePhone = "deve ser um número de celular válido"
            countryCode = "deve ser um código telefônico de país válido"
            areaCode = "deve ser um código de área válido"
        }
    )
)

internal val phoneMessages: PhoneMessages
    get() = PHONE_MESSAGES.current

/**
 * Changes the messages of the phone constraints.
 *
 * ```kotlin
 * Validity.configure { messages { phone { mobilePhone = "celular inválido" } } }
 * ```
 *
 * @param block sets the messages it changes. The others keep their default.
 */
public fun Messages.phone(
    block: PhoneMessages.() -> Unit
): Unit = module(PHONE_MESSAGES).block()
