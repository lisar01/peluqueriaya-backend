package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.controller.Items
import ar.edu.unq.peluqueriayabackend.service.MapsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MapsServiceImpl(
        val revGeocodClient: WebClient,
        val geocodClient: WebClient,
        @Value("\${apiKey}") val apiKey: String): MapsService {

    override fun getLocationByCoords(coords: String): Mono<Items> {
        return revGeocodClient.get()
                .uri{it.queryParam("apiKey", apiKey)
                        .queryParam("at", coords)
                        .build()
                }
                .retrieve()
                .bodyToMono()
    }

    override fun getLocationByAddress(query: String): Flux<Items> {
        return geocodClient.get()
                .uri{it.queryParam("apiKey", apiKey)
                        .queryParam("q", query)
                        .queryParam("in", "countryCode:ARG")
                        .build()
                }
                .retrieve()
                .bodyToFlux()
    }

}