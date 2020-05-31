package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.TurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.SolicitudTurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.ClienteTurnoDTO
import ar.edu.unq.peluqueriayabackend.exception.*
import ar.edu.unq.peluqueriayabackend.model.ServicioInfo
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.service.ClienteService
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import ar.edu.unq.peluqueriayabackend.service.RolService
import ar.edu.unq.peluqueriayabackend.service.TurnoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/turno")
@Validated
class TurnoController(
        val rolService: RolService,
        @Autowired val turnoService: TurnoService,
        @Autowired val clienteService: ClienteService,
        @Autowired val peluqueroService: PeluqueroService)
{

    @GetMapping("/peluquero/{id}")
    fun turnosDelPeluquero(@Valid @PathVariable("id") idPeluquero:Long, esHistorico: Boolean, pageable: Pageable) : Page<Turno> {
        //TODO
        // VALIDAR SI POSEE PERMISOS PARA ACCEDER A LOS TURNOS DEL PELUQUERO

        val maybePeluquero = peluqueroService.get(idPeluquero)
        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException(idPeluquero)

        //Si esHistorico retorna los turnos FINALIZADOS sino los turnos PENDIENTES o CONFIRMADOS
        return if(esHistorico){
            turnoService.obtenerTurnosHistoricosDelPeluquero(maybePeluquero.get(),pageable)
        }else{
            turnoService.obtenerTurnosPendientesOConfirmadosDelPeluquero(maybePeluquero.get(),pageable)
        }
    }

    @PostMapping("/pedir")
    fun pedirTurno(@Valid @RequestBody solicitudTurnoDTO: SolicitudTurnoDTO) : Turno {
        val clienteId = rolService.getEmail()
        val mayBeClient = clienteService.getByEmail(clienteId)

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

        if(! maybeTurno.get().getEstaEsperando())
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