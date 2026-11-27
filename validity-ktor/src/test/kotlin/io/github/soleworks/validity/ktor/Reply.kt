package io.github.soleworks.validity.ktor

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.application.Application
import io.ktor.server.testing.testApplication

data class Reply(
    val status: HttpStatusCode,
    val body: String
)

fun replyTo(
    path: String,
    json: String,
    module: Application.() -> Unit
): Reply {
    lateinit var reply: Reply
    testApplication {
        application(module)
        val response = client.post(path) {
            contentType(ContentType.Application.Json)
            setBody(json)
        }
        reply = Reply(status = response.status, body = response.bodyAsText())
    }
    return reply
}
