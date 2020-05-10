package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/peluquero")
@Validated
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscar(@Valid ubicacion: Ubicacion, @Valid filtro: Filtro?, pageable: Pageable): Page<Peluquero> {
        return peluqueroService.buscar(ubicacion, filtro, pageable)
    }
}