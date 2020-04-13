package ar.edu.unq.peluqueriayabackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {

    @Bean
    fun geocodingClient(): WebClient {
        return WebClient.builder()
                .baseUrl("https://revgeocode.search.hereapi.com/v1/revgeocode")
                .build()
    }

}
