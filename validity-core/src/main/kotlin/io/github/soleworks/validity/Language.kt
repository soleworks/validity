package io.github.soleworks.validity

import java.util.Locale

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
public enum class Language(
    private val tag: String
) {
    /** English, the language of the messages unless another one is picked. */
    EN("en"),

    /** Spanish. */
    ES("es"),

    /** Portuguese, as written in Brazil. */
    PT_BR("pt-BR");

    /** Finds a [Language] by its tag. */
    public companion object {
        /**
         * Returns the language of [tag], read by its language and not by its region: `es-AR` is [ES], and `pt` or
         * `pt-PT` is [PT_BR]. Case does not matter, and `_` works as `-`.
         *
         * ```kotlin
         * Validity.configure { language = Language.of(System.getenv("APP_LANGUAGE")) }
         * ```
         *
         * @param tag an IETF language tag, such as `pt-BR`, `es` or `en-US`.
         * @return the language whose messages Validity has for [tag].
         * @throws IllegalArgumentException when Validity has no messages in the language of [tag].
         */
        public fun of(
            tag: String
        ): Language {
            val language = Locale.forLanguageTag(tag.replace(oldChar = '_', newChar = '-')).language

            return requireNotNull(entries.firstOrNull { entry -> entry.tag.substringBefore('-') == language }) {
                "Language.of has no language for '$tag': the languages are ${entries.joinToString { it.tag }}"
            }
        }
    }
}
