package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.dtos.Items
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import reactor.core.publisher.Mono

interface MapasService {
    fun obtenerUbicacionConCoords(coords: String): Mono<Items>
    fun obtenerUbicacionConDireccion(direccion: String): Mono<List<Ubicacion>>
}