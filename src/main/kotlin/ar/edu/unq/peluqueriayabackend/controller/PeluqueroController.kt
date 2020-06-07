package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.controller.dtos.PeluqueroDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.PeluqueroSimpleDTO
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroNoExisteException
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroYaExisteException
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/peluquero")
@Validated
class PeluqueroController(
        val rolService: RolService,
        @Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscar(@Valid ubicacion: Ubicacion, @Valid filtro: Filtro?, pageable: Pageable): Page<Peluquero> {
        return peluqueroService.buscar(ubicacion, filtro, pageable)
    }

    @GetMapping("/{id}")
    fun getPeluquero(@PathVariable("id") @Valid idPeluquero : Long): Peluquero {
        val maybePeluquero = peluqueroService.get(idPeluquero)
        if(!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()

        return maybePeluquero.get()
    }

    @GetMapping
    fun getPeluqueroLogged() : Peluquero {
        val maybePeluquero = getMaybePeluqueroByJWT()
        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()

        return maybePeluquero.get()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK, reason = "Cuenta de peluquero creada exitosamente!")
    fun guardar(@Valid @RequestBody peluqueroDTO: PeluqueroDTO) {
        if (rolService.tieneRolPeluquero()) {
            throw PeluqueroYaExisteException()
        }
        peluqueroService.save(peluqueroDTO.toPeluquero(rolService.getEmail()))
    }

    //La idea de este desconectar no es el logout del usuario sino que deje de recibir turnos o aparecer en la busqueda de peluqueros
    @PostMapping("/desconectar")
    fun desconectarPeluquero() : Peluquero {
        val maybePeluquero = getMaybePeluqueroByJWT()
        if(!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()
        return peluqueroService.desconectar(maybePeluquero.get())
    }

    //La idea de este conectar no es el login del usuario sino que pueda recibir turnos o aparecer en la busqueda de peluqueros
    @PostMapping("/conectar")
    fun conectarPeluquero():Peluquero {
        val maybePeluquero = getMaybePeluqueroByJWT()
        if(!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()
        return peluqueroService.conectar(maybePeluquero.get())
    }

    private fun getMaybePeluqueroByJWT(): Optional<Peluquero> {
        val emailPeluquero = rolService.getEmail()
        return peluqueroService.getByEmail(emailPeluquero)
    }
}