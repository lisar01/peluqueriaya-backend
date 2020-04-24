package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Items
import ar.edu.unq.peluqueriayabackend.service.MapasService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank


@RestController
@RequestMapping("/mapas")
@Validated
class MapasController(val mapasService: MapasService) {

    @GetMapping("/reversegeocoding")
    fun obtenerUbicacionConCoords(@RequestParam @NotBlank(message = "{latitud.vacio}") latitude: String,
                                  @RequestParam @NotBlank(message = "{longitud.vacio}") longitude: String): Mono<Items> {
        val coords = "$latitude,$longitude"
        return mapasService.obtenerUbicacionConCoords(coords)
    }

    @GetMapping("/geocoding")
    fun obtenerUbicacionConDireccion(@RequestParam
                                     @NotBlank(message = "{direccion.vacio}")
                                     @Min(10, message = "{direccion.minimo}") direccion: String): Mono<Items> {
        return mapasService.obtenerUbicacionConDireccion(direccion)
    }

}