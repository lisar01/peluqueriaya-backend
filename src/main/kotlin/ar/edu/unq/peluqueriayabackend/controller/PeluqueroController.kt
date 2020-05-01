package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/peluquero")
@Validated
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscarPeluqueros(@RequestParam
                         @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                         latitude: String,
                         @RequestParam
                         @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                         longitude: String): List<Peluquero> {
        val ubicacion = Ubicacion(latitude, longitude)
        return peluqueroService.buscarPeluquerosCercanos(ubicacion)
    }

    @GetMapping("/search/servicio/tipo")
    fun buscarPeluquerosPorTiposdeServicio(@RequestParam
                                           @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                                           latitude: String,
                                           @RequestParam
                                           @Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
                                           longitude: String,
                                           @RequestParam
                                           tipoDeServicio: ServicioType): List<Peluquero> {
        val ubicacion = Ubicacion(latitude, longitude)
        return peluqueroService.buscarPeluquerosCercanosPorTipoDeServicio(ubicacion, tipoDeServicio)
    }

}