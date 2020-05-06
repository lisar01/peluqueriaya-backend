package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@RestController
@RequestMapping("/peluquero")
@Validated
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscar(@Valid ubicacion: Ubicacion, @Valid filtro: Filtro?, pageable: Pageable): Page<Peluquero> {
        return peluqueroService.buscar(ubicacion, filtro, pageable)
    }

    @GetMapping("/search")
    fun buscarPeluqueros(@RequestParam
                         @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                         latitude: String,
                         @RequestParam
                         @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                         longitude:String): List<Peluquero> =
            peluqueroService.buscarPeluquerosCercanos(Ubicacion(latitude,longitude), Pageable.unpaged()).toList()

    @GetMapping("/search/nombre-tipo")
    fun buscarPeluquerosPorNombreOTipo(@RequestParam
                                       @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                                       latitude : String,
                                       @RequestParam
                                       @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                                       longitude:String,
                                       @RequestParam
                                       @Size(max = 25, message = "{input.muyLargo}")
                                       nombreOTipo:String):List<Peluquero> =
        peluqueroService.buscarPeluquerosCercanosPorNombreOTipo(Ubicacion(latitude,longitude),nombreOTipo,Pageable.unpaged()).toList()

    @GetMapping("/search/servicio/tipo")
    fun buscarPeluquerosPorTiposdeServicio(@RequestParam
                                           @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                                           latitude: String,
                                           @RequestParam
                                           @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                                           longitude: String,
                                           @RequestParam
                                           tipoDeServicio: ServicioType): List<Peluquero> =
        peluqueroService.buscarPeluquerosCercanosPorTipoDeServicio(Ubicacion(latitude, longitude), tipoDeServicio, Pageable.unpaged()).toList()

}