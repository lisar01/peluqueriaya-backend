package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/peluquero")
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscarPeluqueros(@RequestParam
                         @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                         latitude : String,
                         @RequestParam
                         @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                         longitude:String): List<Peluquero> =
            peluqueroService.buscarPeluquerosCercanos(Ubicacion(latitude,longitude), Pageable.unpaged()).toList()

    @GetMapping("/search/nombre-tipo")
    fun buscarPeluquerosPorNombreOTipo(@RequestParam
                                       @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}")
                                       latitude : String,
                                       @RequestParam
                                       @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{latitud.invalida}") longitude:String,
                                       @RequestParam nombreOTipo:String):List<Peluquero> =
        peluqueroService.buscarPeluquerosCercanosPorNombreOTipo(Ubicacion(latitude,longitude),nombreOTipo,Pageable.unpaged()).toList()
}