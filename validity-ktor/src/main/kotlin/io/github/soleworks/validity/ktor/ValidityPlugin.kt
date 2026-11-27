package io.github.soleworks.validity.ktor

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.ValidationException
import io.github.soleworks.validity.validateOrThrow
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.BaseApplicationPlugin
import io.ktor.server.application.call
import io.ktor.server.request.ApplicationReceivePipeline
import io.ktor.util.AttributeKey

/**
 * The Ktor plugin of Validity: every request body that implements [Validatable] is validated when a route receives
 * it, and every broken rule of a call is answered for the route.
 *
 * Once it is installed, `call.receive<T>()` validates the body and throws a [ValidationException] when a rule is
 * broken, so the route only runs with a valid body. Each [ValidationException] of a call, from `receive` or from a
 * `validateOrThrow` the route calls, such as the one of a
 * [ContextualValidatable][io.github.soleworks.validity.ContextualValidatable], is answered with `400 Bad Request`
 * and a body holding `title`, `status` and the `violations`, each with its `path`, `code` and `message`. The body is
 * written by the `ContentNegotiation` of the application, in its format. [ValidityPluginConfig.onInvalid] answers
 * something else, and is the answer of an application without `ContentNegotiation`, where Ktor would answer
 * `406 Not Acceptable`.
 *
 * ```kotlin
 * install(ContentNegotiation) { json() }
 * install(ValidityPlugin)
 *
 * routing {
 *     post("/registrations") {
 *         val registration = call.receive<Registration>()   // valid here
 *         call.respond(HttpStatusCode.Created)
 *     }
 * }
 * ```
 */
public object ValidityPlugin : BaseApplicationPlugin<Application, ValidityPluginConfig, ValidityPluginConfig> {
    /** The key the plugin is installed under. */
    override val key: AttributeKey<ValidityPluginConfig> = AttributeKey("Validity")

    /**
     * Installs the plugin in [pipeline], with the configuration that [configure] sets.
     *
     * @param pipeline the application the plugin validates the calls of.
     * @param configure sets the configuration, such as [ValidityPluginConfig.onInvalid].
     */
    override fun install(
        pipeline: Application,
        configure: ValidityPluginConfig.() -> Unit
    ): ValidityPluginConfig {
        val config = ValidityPluginConfig().apply(configure)

        pipeline.receivePipeline.intercept(ApplicationReceivePipeline.After) { body ->
            if (body is Validatable) body.validateOrThrow()
        }
        pipeline.intercept(ApplicationCallPipeline.Plugins) {
            try {
                proceed()
            } catch (error: ValidationException) {
                config.respond(call, error.violations)
            }
        }

        return config
    }
}
