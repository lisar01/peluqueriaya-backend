package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.controller.dtos.Items
import ar.edu.unq.peluqueriayabackend.service.MapasService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class MapasServiceImpl(
        val revGeocodClient: WebClient,
        val geocodClient: WebClient,
        @Value("\${apiKey}") val apiKey: String): MapasService {

    override fun obtenerUbicacionConCoords(coords: String): Mono<Items> {
        return revGeocodClient.get()
                .uri{it.queryParam("apiKey", apiKey)
                        .queryParam("at", coords)
                        .queryParam("limit", 1)
                        .build()
                }
                .retrieve()
                .bodyToMono()
    }

    override fun obtenerUbicacionConDireccion(direccion: String): Mono<Items> {
        return geocodClient.get()
                .uri{it.queryParam("apiKey", apiKey)
                        .queryParam("q", direccion)
                        .queryParam("in", "countryCode:ARG")
                        .build()
                }
                .retrieve()
                .bodyToMono()
    }

}