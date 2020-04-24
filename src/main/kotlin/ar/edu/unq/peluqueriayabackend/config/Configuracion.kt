package ar.edu.unq.peluqueriayabackend.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class Configuracion {

    @Bean
    fun revGeocodClient(): WebClient {
        return WebClient.builder()
                .baseUrl("https://revgeocode.search.hereapi.com/v1/revgeocode")
                .build()
    }

    @Bean
    fun geocodClient(): WebClient {
        return WebClient.builder()
                .baseUrl("https://geocode.search.hereapi.com/v1/geocode")
                .build()
    }

    @Bean
    fun messageSource(): MessageSource? {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:mensajes")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun getValidator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource()!!)
        return bean
    }

}
