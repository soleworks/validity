package io.github.soleworks.validity.samples.docs

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.constraints.containsNone
import io.github.soleworks.validity.constraints.distinctBy
import io.github.soleworks.validity.constraints.notBlank
import io.github.soleworks.validity.constraints.sizeBetween
import io.github.soleworks.validity.each
import io.github.soleworks.validity.ifPresent
import io.github.soleworks.validity.required
import io.github.soleworks.validity.validation

private val BANNED_TAGS = listOf("spam", "nsfw")

data class Playlist(
    val tracks: List<Track>?,
    val tags: Set<String>? = null
) : Validatable {
    override fun validation() = validation {
        ::tracks required {
            sizeBetween(min = 1, max = 200)
            distinctBy { it.id }
        }
        ::tags ifPresent {
            containsNone(BANNED_TAGS)
            each { notBlank() }
        }
    }
}
