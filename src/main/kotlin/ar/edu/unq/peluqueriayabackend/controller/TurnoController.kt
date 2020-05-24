package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.TurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.SolicitudTurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.ClienteTurnoDTO
import ar.edu.unq.peluqueriayabackend.exception.*
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
           solicitudTurnoDTO.serviciosSolicitadosId.contains(it.id)
        }.map {
            ServicioInfo.Builder().
                    withNombre(it.nombre).
                    withPrecio(it.precio).
                    build()
        }

        return turnoService.pedirTurno(
                mayBeClient.get(),
                maybePeluquero.get(),
                serviciosSolicitadosInfo,
                solicitudTurnoDTO.ubicacion)
    }

    @PostMapping("/confirmar")
    fun confirmarTurno(@Valid @RequestBody turnoDTO: TurnoDTO):Turno {

        return turnoService.confirmarTurno(validateTurnoDTOYPeluquero(turnoDTO))
    }

    @PostMapping("/finalizar")
    fun finalizarTurno(@Valid @RequestBody turnoDTO: TurnoDTO):Turno {
        return turnoService.finalizarTurno(validateTurnoDTOYPeluquero(turnoDTO))
    }

    @PostMapping("/cancelar")
    fun cancelarTurno(@Valid @RequestBody clienteTurnoDTO: ClienteTurnoDTO) : Turno {
        return turnoService.cancelarTurno(validateUserCancelarTurnoDTO(clienteTurnoDTO))
    }

    private fun validateUserCancelarTurnoDTO(clienteTurnoDTO: ClienteTurnoDTO) : Turno {
        val maybeCliente = clienteService.get(clienteTurnoDTO.clienteId)
        if(! maybeCliente.isPresent)
            throw ClienteNoExisteException(clienteTurnoDTO.clienteId)

        val maybeTurno = turnoService.get(clienteTurnoDTO.turnoId)
        if(! maybeTurno.isPresent)
            throw TurnoNoExisteException(clienteTurnoDTO.turnoId)

        if(maybeTurno.get().getClienteId() != clienteTurnoDTO.clienteId)
            throw Unauthorized("No tiene acceso a este recurso")

        if(! maybeTurno.get().estaEsperando())
            throw TurnoNoPuedeSerCancelado()

        return maybeTurno.get()
    }

    private fun validateTurnoDTOYPeluquero(turnoDTO:TurnoDTO) : Turno{
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