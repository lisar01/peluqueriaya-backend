package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.exception.UbicacionErroneaExcepcion
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/peluquero")
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscarPeluqueros(@RequestParam latitude : String,@RequestParam longitude:String): List<Peluquero> {
        val ubicacion = Ubicacion(latitude,longitude)

        validarUbicacion(ubicacion)

        return peluqueroService.buscarPeluquerosCercanos(ubicacion, Pageable.unpaged()).toList()
    }

    @GetMapping("/search/nombre-tipo")
    fun buscarPeluquerosPorNombreOTipo(@RequestParam latitude: String, @RequestParam longitude: String, @RequestParam nombreOTipo:String):List<Peluquero>{
        val ubicacion = Ubicacion(latitude,longitude)

        validarUbicacion(ubicacion)

        return peluqueroService.buscarPeluquerosCercanosPorNombreOTipo(ubicacion,nombreOTipo,Pageable.unpaged()).toList()
    }

    private fun validarUbicacion(ubicacion: Ubicacion){
        if(! ubicacion.isValid())
            throw UbicacionErroneaExcepcion()
    }
}