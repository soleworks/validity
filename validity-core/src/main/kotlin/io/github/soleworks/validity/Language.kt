package io.github.soleworks.validity

/**
 * A language the built-in messages are written in.
 *
 * Pick one for the whole application with [ValidityConfiguration.language], or for one call with the `language` of
 * [validate][io.github.soleworks.validity.validate]:
 *
 * ```kotlin
 * Validity.configure { language = Language.PT_BR }
 *
 * user.validate(language = Language.ES)
 * ```
 */
public enum class Language {
    /** English, the language of the messages unless another one is picked. */
    EN,

    /** Spanish. */
    ES,

    /** Portuguese, as written in Brazil. */
    PT_BR
}
