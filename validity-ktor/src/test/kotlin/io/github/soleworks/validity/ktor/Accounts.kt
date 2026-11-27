package io.github.soleworks.validity.ktor

import io.github.soleworks.validity.ktor.samples.docs.accounts
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.contentnegotiation.ContentNegotiationConfig
import io.ktor.server.routing.routing

fun Application.accounts(
    serialization: ContentNegotiationConfig.() -> Unit = { json() },
    validity: ValidityPluginConfig.() -> Unit = {}
) {
    install(ContentNegotiation, serialization)
    install(ValidityPlugin, validity)
    routing { accounts(team = TeamOfAna) }
}
