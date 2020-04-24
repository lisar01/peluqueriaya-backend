package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.dtos.Items
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono
import javax.validation.constraints.Pattern

@Validated
interface MapasService {
    fun obtenerUbicacionConCoords(
            @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?,\\s*-?[1-9][0-9]*(\\.[0-9]+)?"
            , message="{coords.invalidas}") coords: String): Mono<Items>
    fun obtenerUbicacionConDireccion(direccion: String): Mono<Items>
}