package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.Items
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MapsService {
    fun getLocationByCoords(coords: String): Mono<Items>
    fun getLocationByAddress(query: String): Flux<Items>
}