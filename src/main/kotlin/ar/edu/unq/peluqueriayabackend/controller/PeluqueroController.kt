package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity

@CrossOrigin
@RestController
@RequestMapping("/peluquero")
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscarPeluqueros(@RequestParam latitude : String,@RequestParam longitude:String): ResponseEntity<Any> {

        val ubicacion = Ubicacion(latitude,longitude)

       if(! ubicacion.isValid())
           return ResponseEntity("Ubicacion erronea",HttpStatus.BAD_REQUEST)

        return ResponseEntity(peluqueroService.buscarPeluquerosCercanos(ubicacion)
                ,HttpStatus.OK)
    }
}