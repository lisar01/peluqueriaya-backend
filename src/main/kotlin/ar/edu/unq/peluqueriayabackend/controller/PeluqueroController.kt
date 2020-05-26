package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.controller.dtos.PeluqueroSimpleDTO
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroNoExisteException
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

    @GetMapping("/{id}")
    fun getPeluquero(@PathVariable("id") @Valid idPeluquero : Long): Peluquero {
        val maybePeluquero = peluqueroService.get(idPeluquero)
        if(!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException(idPeluquero)

        return maybePeluquero.get()
    }

    //La idea de este desconectar no es el logout del usuario sino que deje de recibir turnos o aparecer en la busqueda de peluqueros
    @PostMapping("/desconectar")
    fun desconectarPeluquero(@Valid @RequestBody peluqueroSimpleDTO: PeluqueroSimpleDTO) : Peluquero {
        val maybePeluquero = peluqueroService.get(peluqueroSimpleDTO.peluqueroId)
        if(!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException(peluqueroSimpleDTO.peluqueroId)
        //TODO
        // VALIDAR SI EL PELUQUERO AUTENTICADO ES EL MISMO CON EL QUE SE VA A MODIFICAR

        return peluqueroService.desconectar(maybePeluquero.get())
    }

    //La idea de este conectar no es el login del usuario sino que pueda recibir turnos o aparecer en la busqueda de peluqueros
    @PostMapping("/conectar")
    fun conectarPeluquero(@Valid @RequestBody peluqueroSimpleDTO: PeluqueroSimpleDTO):Peluquero {
        val maybePeluquero = peluqueroService.get(peluqueroSimpleDTO.peluqueroId)
        if(!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException(peluqueroSimpleDTO.peluqueroId)
        //TODO
        // VALIDAR SI EL PELUQUERO AUTENTICADO ES EL MISMO CON EL QUE SE VA A MODIFICAR
        return peluqueroService.conectar(maybePeluquero.get())
    }

}