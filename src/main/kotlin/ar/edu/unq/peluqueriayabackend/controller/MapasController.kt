package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Items
import ar.edu.unq.peluqueriayabackend.service.MapasService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/mapas")
class MapasController(val mapasService: MapasService) {

    @GetMapping("/reversegeocoding")
    fun obtenerUbicacionConCoords(@RequestParam latitude: String, @RequestParam longitude: String): Mono<Items> {
        val coords = "$latitude,$longitude"
        return mapasService.obtenerUbicacionConCoords(coords)
    }

    @GetMapping("/geocoding")
    fun obtenerUbicacionConDireccion(@RequestParam direccion: String): Mono<Items> {
        return mapasService.obtenerUbicacionConDireccion(direccion)
    }

}