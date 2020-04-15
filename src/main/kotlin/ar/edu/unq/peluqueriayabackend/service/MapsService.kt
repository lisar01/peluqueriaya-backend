package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.Items
import reactor.core.publisher.Mono

interface MapsService {
    fun getLocationByCoords(coords: String): Mono<Items>
}