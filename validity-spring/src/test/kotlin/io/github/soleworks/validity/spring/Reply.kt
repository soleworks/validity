package io.github.soleworks.validity.spring

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.springframework.boot.WebApplicationType
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext

// Spring Boot 3 writes these two fields of a ProblemDetail and Spring Boot 4 leaves them out, so a reply ignores them.
private val FIELDS_OF_SPRING_BOOT_3 = setOf("type", "instance")

data class Reply(
    val status: Int,
    val body: JsonObject
)

fun json(
    text: String
): JsonObject = Json.parseToJsonElement(text).jsonObject

fun replyTo(
    path: String,
    json: String,
    stack: WebApplicationType,
    onInvalid: OnInvalid? = null
): Reply {
    val application = SpringApplicationBuilder(AccountsApplication::class.java).web(stack)
    if (onInvalid != null) application.initializers(beanOf(onInvalid))
    return application.run("--server.port=0", "--spring.main.banner-mode=off").use { context ->
        val port = context.environment.getProperty("local.server.port")
        val request = HttpRequest
            .newBuilder(URI("http://localhost:$port$path"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build()
        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        val body = if (response.body().isBlank()) JsonObject(emptyMap()) else json(response.body())
        Reply(status = response.statusCode(), body = JsonObject(body - FIELDS_OF_SPRING_BOOT_3))
    }
}

private fun beanOf(
    onInvalid: OnInvalid
) = ApplicationContextInitializer<ConfigurableApplicationContext> { context ->
    context.beanFactory.registerSingleton("onInvalid", onInvalid)
}
