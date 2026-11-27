package io.github.soleworks.validity.spring

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.reactive.DispatcherHandler
import org.springframework.web.servlet.DispatcherServlet

@AutoConfiguration
internal class ValidityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    fun onInvalid(): OnInvalid = BadRequest

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnClass(DispatcherServlet::class)
    @Import(ValidatingBodyAdvice::class, ServletValidationExceptionHandler::class)
    class Servlet

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnClass(DispatcherHandler::class)
    @Import(ValidatingReaders::class, ReactiveValidationExceptionHandler::class)
    class Reactive
}
