package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.SolicitudTurnoDTO
import ar.edu.unq.peluqueriayabackend.exception.*
import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.model.Peluquero
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
import java.util.*
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

    @GetMapping("/peluquero")
    fun turnosDelPeluquero(@Valid esHistorico: Boolean, pageable: Pageable) : Page<Turno> {
        val maybePeluquero = getMaybePeluqueroByJWT()
        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()

        //Si esHistorico retorna los turnos FINALIZADOS sino los turnos PENDIENTES o CONFIRMADOS
        return if(esHistorico){
            turnoService.obtenerTurnosHistoricosDelPeluquero(maybePeluquero.get(),pageable)
        }else{
            turnoService.obtenerTurnosPendientesOConfirmadosDelPeluquero(maybePeluquero.get(),pageable)
        }
    }

    @PostMapping("/pedir")
    fun pedirTurno(@Valid @RequestBody solicitudTurnoDTO: SolicitudTurnoDTO) : Turno {
        val mayBeClient = getMaybeClienteByJWT()

        val maybePeluquero = peluqueroService.get(solicitudTurnoDTO.idPeluquero)

        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()

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

    @PostMapping("/confirmar/{idTurno}")
    fun confirmarTurno(@Valid @PathVariable("idTurno") idTurno: Long):Turno {
        return turnoService.confirmarTurno(validarIdTurnoYPeluquero(idTurno))
    }

    @PostMapping("/finalizar/{idTurno}")
    fun finalizarTurno(@Valid @PathVariable("idTurno") idTurno: Long):Turno {
        return turnoService.finalizarTurno(validarIdTurnoYPeluquero(idTurno))
    }

    @PostMapping("/cancelar/{idTurno}")
    fun cancelarTurno(@Valid @PathVariable("idTurno") idTurno: Long) : Turno {
        return turnoService.cancelarTurno(validarIdTurnoYCliente(idTurno))
    }

    private fun getMaybePeluqueroByJWT(): Optional<Peluquero> {
        val emailPeluquero = rolService.getEmail()
        return peluqueroService.getByEmail(emailPeluquero)
    }

    private fun getMaybeClienteByJWT(): Optional<Cliente> {
        val emailCliente = rolService.getEmail()
        return clienteService.getByEmail(emailCliente)
    }

    private fun validarIdTurnoYCliente(idTurno: Long) : Turno {
        val maybeCliente = getMaybeClienteByJWT()
        if(! maybeCliente.isPresent)
            throw ClienteNoExisteException()

        val maybeTurno = turnoService.get(idTurno)
        if(! maybeTurno.isPresent)
            throw TurnoNoExisteException(idTurno)

        if(maybeTurno.get().getClienteId() != maybeCliente.get().id)
            throw Unauthorized("No tiene acceso a este recurso")

        if(! maybeTurno.get().getEstaEsperando())
            throw TurnoNoPuedeSerCancelado()

        return maybeTurno.get()
    }

    private fun validarIdTurnoYPeluquero(idTurno: Long) : Turno{
        val maybePeluquero = getMaybePeluqueroByJWT()
        if(! maybePeluquero.isPresent)
            throw PeluqueroNoExisteException()

        val maybeTurno = turnoService.get(idTurno)
        if(! maybeTurno.isPresent)
            throw TurnoNoExisteException(idTurno)

        if(maybeTurno.get().getPeluqueroId()!! != maybePeluquero.get().id)
            throw Unauthorized("No tiene acceso a este recurso")

        return maybeTurno.get()
    }
}