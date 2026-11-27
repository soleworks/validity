package io.github.soleworks.validity.spring

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter

internal class ValidatingReaders : BeanPostProcessor {
    override fun postProcessBeforeInitialization(
        bean: Any,
        beanName: String
    ): Any {
        if (bean is RequestMappingHandlerAdapter) bean.validateBodies()
        return bean
    }
}

private fun RequestMappingHandlerAdapter.validateBodies() {
    messageReaders = messageReaders.map { reader -> ValidatingReader(reader) }
}
