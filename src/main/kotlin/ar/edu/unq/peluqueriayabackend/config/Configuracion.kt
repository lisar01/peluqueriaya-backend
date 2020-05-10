package ar.edu.unq.peluqueriayabackend.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.reactive.function.client.WebClient
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.concurrent.Executor


@Configuration
@EnableSwagger2
@EnableAsync
class Configuracion {

    @Bean("threadPoolTaskExecutor")
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2
        executor.maxPoolSize = 10
        executor.setQueueCapacity(20)
        executor.initialize()
        return executor
    }

    @Bean
    fun customerAPI(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.peluqueriayabackend.controller"))
                .paths(PathSelectors.any())
                .build()
    }

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
        messageSource.setDefaultEncoding("ISO-8859-1")
        return messageSource
    }

    @Bean
    fun getValidator(): LocalValidatorFactoryBean? {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource()!!)
        return bean
    }

}
