package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.TurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.SolicitudTurnoDTO
import ar.edu.unq.peluqueriayabackend.exception.ClienteNoExisteException
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroNoExisteException
import ar.edu.unq.peluqueriayabackend.exception.TurnoNoExisteException
import ar.edu.unq.peluqueriayabackend.exception.Unauthorized
import ar.edu.unq.peluqueriayabackend.model.ServicioInfo
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.service.ClienteService
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import ar.edu.unq.peluqueriayabackend.service.TurnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/turno")
@Validated
class TurnoController(
        @Autowired val turnoService: TurnoService,
        @Autowired val clienteService: ClienteService,
        @Autowired val peluqueroService: PeluqueroService)
{

    @PostMapping("/pedir")
    fun pedirTurno(@Valid @RequestBody solicitudTurnoDTO: SolicitudTurnoDTO) : Turno {

        val mayBeClient = clienteService.get(solicitudTurnoDTO.idCliente)

        //TODO
        // CHEQUEAR QUE EL CLIENTE AUTENTICADO SEA EL MISMO QUE EL CLIENTE
        if (! mayBeClient.isPresent )
            throw ClienteNoExisteException(solicitudTurnoDTO.idCliente)

        val maybePeluquero = peluqueroService.get(solicitudTurnoDTO.idPeluquero)

        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException(solicitudTurnoDTO.idPeluquero)


        val serviciosSolicitadosInfo = maybePeluquero.get().servicios.filter {
            s -> solicitudTurnoDTO.serviciosSolicitadosId.contains(s.id)
        }.map {
            s -> ServicioInfo.Builder().
                    withNombre(s.nombre).
                    withPrecio(s.precio).
                    build()
        }

        return turnoService.pedirTurno(
                mayBeClient.get(),
                maybePeluquero.get(),
                serviciosSolicitadosInfo)
    }

    @PostMapping("/confirmar")
    fun confirmarTurno(@Valid @RequestBody turnoDTO: TurnoDTO):Turno {

        return turnoService.confirmarTurno(validateTurnoDTO(turnoDTO))
    }

    @PostMapping("/finalizar")
    fun finalizarTurno(@Valid @RequestBody turnoDTO: TurnoDTO):Turno {
        return turnoService.finalizarTurno(validateTurnoDTO(turnoDTO))
    }

    private fun validateTurnoDTO(turnoDTO:TurnoDTO) : Turno{
        val maybePeluquero = peluqueroService.get(turnoDTO.peluqueroId)
        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException(turnoDTO.peluqueroId)

        val maybeTurno = turnoService.get(turnoDTO.turnoId)
        if(! maybeTurno.isPresent)
            throw TurnoNoExisteException(turnoDTO.turnoId)

        if(maybeTurno.get().getPeluqueroId()!! != turnoDTO.peluqueroId)
            throw Unauthorized("No tiene acceso a este recurso")

        return maybeTurno.get()
    }
}