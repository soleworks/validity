package io.github.soleworks.validity.ktor.samples.docs

import io.github.soleworks.validity.validateOrThrow
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.accounts(
    team: Team
) {
    post("/registrations") {
        call.receive<Registration>()
        call.respond(HttpStatusCode.Created)
    }
    post("/invitations") {
        call.receive<Invitation>().validateOrThrow(team)
        call.respond(HttpStatusCode.Created)
    }
}
