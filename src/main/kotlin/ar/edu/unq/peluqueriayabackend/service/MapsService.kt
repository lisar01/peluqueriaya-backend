package ar.edu.unq.peluqueriayabackend.service

import reactor.core.publisher.Mono

interface MapsService {
    fun getLocationByCoords(coords: String): Mono<String>
}