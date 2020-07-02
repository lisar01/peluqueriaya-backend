package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.controller.dtos.peluquero.*
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroNoExisteException
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroYaExisteException
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import ar.edu.unq.peluqueriayabackend.service.RolService
import ar.edu.unq.peluqueriayabackend.service.TurnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/peluquero")
@Validated
class PeluqueroController(
        val rolService: RolService,
        @Autowired val turnoService: TurnoService,
        @Autowired val peluqueroService: PeluqueroService) {

    @GetMapping("/search")
    fun buscar(@Valid ubicacion: Ubicacion, @Valid filtro: Filtro?, pageable: Pageable): Page<PeluqueroConPuntuacionDTO> {
        val peluqueros = peluqueroService.buscar(ubicacion, filtro, pageable)
        return peluqueros.map { obtenerPeluqueroConPuntuacionPromedio(it) }
    }

    @GetMapping("/{id}")
    fun getPeluquero(@PathVariable("id") @Valid idPeluquero: Long): PeluqueroConPuntuacionDTO {
        val maybePeluquero = peluqueroService.get(idPeluquero)
        if (!maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()

        return obtenerPeluqueroConPuntuacionPromedio(maybePeluquero.get())
    }

    @GetMapping
    fun getPeluqueroLogged(): PeluqueroConPuntuacionDTO = obtenerPeluqueroConPuntuacionPromedio(getMaybePeluqueroByJWT().get())

    @GetMapping("/servicios")
    fun getServicios(): List<Servicio> {
        return peluqueroService.getServiciosByEmail(rolService.getEmail())
    }

    @PostMapping
    fun guardar(@Valid @RequestBody peluqueroNuevoDTO: PeluqueroNuevoDTO): PeluqueroPerfilDTO {
        if (rolService.tieneRolPeluquero()) {
            throw PeluqueroYaExisteException()
        }
        val peluqueroAGuardar = PeluqueroMapper.dtoNuevoAPeluquero(rolService.getEmail(), peluqueroNuevoDTO)
        return PeluqueroMapper.peluqueroADto(peluqueroService.save(peluqueroAGuardar))
    }

    @PutMapping
    fun editar(@Valid @RequestBody peluqueroEditadoDTO: PeluqueroEditadoDTO): PeluqueroPerfilDTO {
        val peluqueroEditado = getMaybePeluqueroByJWT().get()
        PeluqueroMapper.dtoEditadoAPeluquero(peluqueroEditadoDTO, peluqueroEditado)
        return PeluqueroMapper.peluqueroADto(peluqueroService.update(peluqueroEditado))
    }

    //La idea de este desconectar no es el logout del usuario sino que deje de recibir turnos o aparecer en la busqueda de peluqueros
    @PostMapping("/desconectar")
    fun desconectarPeluquero(): Peluquero = peluqueroService.desconectar(getMaybePeluqueroByJWT().get())

    //La idea de este conectar no es el login del usuario sino que pueda recibir turnos o aparecer en la busqueda de peluqueros
    @PostMapping("/conectar")
    fun conectarPeluquero(): Peluquero = peluqueroService.conectar(getMaybePeluqueroByJWT().get())

    private fun getMaybePeluqueroByJWT(): Optional<Peluquero> {
        val emailPeluquero = rolService.getEmail()
        return peluqueroService.getByEmail(emailPeluquero)
    }

    private fun obtenerPeluqueroConPuntuacionPromedio(peluquero: Peluquero): PeluqueroConPuntuacionDTO {

        var puntuacionPromedio: Double = 0.toDouble()

        try {
            puntuacionPromedio = turnoService.puntuacionPromedioDelPeluquero(peluquero)
        } catch (e: Exception) {

        }

        return PeluqueroConPuntuacionDTO.Builder().withPeluquero(peluquero).withPuntuacionPromedio(puntuacionPromedio).build()
    }
}