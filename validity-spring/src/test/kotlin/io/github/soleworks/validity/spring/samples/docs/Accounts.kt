package io.github.soleworks.validity.spring.samples.docs

import io.github.soleworks.validity.validateOrThrow
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class Accounts(
    private val registrations: Registrations,
    private val team: Team
) {
    @PostMapping("/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(
        @RequestBody registration: Registration
    ) {
        registrations.save(registration)
    }

    @PostMapping("/invitations")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun invite(
        @RequestBody invitation: Invitation
    ) {
        invitation.validateOrThrow(team)
    }
}
