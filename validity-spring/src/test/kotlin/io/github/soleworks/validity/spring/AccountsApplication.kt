package io.github.soleworks.validity.spring

import io.github.soleworks.validity.spring.samples.docs.Accounts
import io.github.soleworks.validity.spring.samples.docs.Registrations
import io.github.soleworks.validity.spring.samples.docs.Team
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@SpringBootConfiguration(proxyBeanMethods = false)
@EnableAutoConfiguration
@Import(Accounts::class)
class AccountsApplication {
    @Bean
    fun registrations(): Registrations = Registrations { }

    @Bean
    fun team(): Team = TeamOfAna
}
