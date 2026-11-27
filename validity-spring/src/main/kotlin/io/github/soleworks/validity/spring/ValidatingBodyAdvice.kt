package io.github.soleworks.validity.spring

import io.github.soleworks.validity.Validatable
import io.github.soleworks.validity.validateOrThrowBlocking
import java.lang.reflect.Type
import org.springframework.core.MethodParameter
import org.springframework.http.HttpInputMessage
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter

@ControllerAdvice
internal class ValidatingBodyAdvice : RequestBodyAdviceAdapter() {
    override fun supports(
        methodParameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean = Validatable::class.java.isAssignableFrom(methodParameter.parameterType)

    override fun afterBodyRead(
        body: Any,
        inputMessage: HttpInputMessage,
        parameter: MethodParameter,
        targetType: Type,
        converterType: Class<out HttpMessageConverter<*>>
    ): Any {
        (body as Validatable).validateOrThrowBlocking()
        return body
    }
}
