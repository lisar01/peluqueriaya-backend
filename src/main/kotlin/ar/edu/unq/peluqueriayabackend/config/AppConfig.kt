package ar.edu.unq.peluqueriayabackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class AppConfig {

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

}
