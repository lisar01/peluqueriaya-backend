package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.config.AppProperties
import ar.edu.unq.peluqueriayabackend.controller.Items
import ar.edu.unq.peluqueriayabackend.service.MapsService
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class MapsServiceImpl(val geocodingClient: WebClient, val appProperties: AppProperties): MapsService {

    override fun getLocationByCoords(coords: String): Mono<Items> {
        return geocodingClient.get()
                .uri{it.queryParam("at", coords)
                        .queryParam("apiKey", appProperties.hereMaps.apiKey)
                        .build()
                }
                .retrieve()
                .bodyToMono()
    }

}