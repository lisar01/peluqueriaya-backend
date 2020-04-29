package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Items
import ar.edu.unq.peluqueriayabackend.service.MapasService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size


@RestController
@RequestMapping("/mapas")
@Validated
class MapasController(val mapasService: MapasService) {

    @GetMapping("/reversegeocoding")
    fun obtenerUbicacionConCoords(@RequestParam
                                  @NotBlank(message = "{latitud.vacio}")
                                  @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                                  latitude: String,
                                  @RequestParam
                                  @NotBlank(message = "{longitud.vacio}")
                                  @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{longitud.invalida}")
                                  longitude: String): Mono<Items> {
        val coords = "$latitude,$longitude"
        return mapasService.obtenerUbicacionConCoords(coords)
    }

    @GetMapping("/geocoding")
    fun obtenerUbicacionConDireccion(@RequestParam
                                     @NotBlank(message = "{direccion.vacio}")
                                     @Size(min = 10, message = "{direccion.minimo}")
                                     direccion: String): Mono<Items> {
        return mapasService.obtenerUbicacionConDireccion(direccion)
    }

}