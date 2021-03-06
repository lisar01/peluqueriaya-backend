package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.CalificacionTurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.SolicitudTurnoDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.TurnoConDireccionDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.TurnoDTO
import ar.edu.unq.peluqueriayabackend.exception.*
import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.ServicioInfo
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.service.*
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
    fun turnosDelPeluquero(@Valid esHistorico: Boolean, pageable: Pageable) : Page<TurnoConDireccionDTO> {
        val maybePeluquero = getMaybePeluqueroByJWT()
        //Si esHistorico retorna los turnos FINALIZADOS sino los turnos PENDIENTES o CONFIRMADOS
        val turnos = if(esHistorico){
            turnoService.obtenerTurnosHistoricosDelPeluquero(maybePeluquero.get(),pageable)
        }else{
            turnoService.obtenerTurnosPendientesOConfirmadosDelPeluquero(maybePeluquero.get(),pageable)
        }
        return turnos.map { convertTurnoToTurnoConDireccionDTO(it) }
    }

    @GetMapping("/cliente")
    fun turnosDelCliente(@Valid esHistorico: Boolean, pageable: Pageable) : Page<TurnoConDireccionDTO> {
        val maybeCliente = getMaybeClienteByJWT()
        //Si esHistorico retorna los turnos FINALIZADOS o CANCELADOS
        // sino los turnos PENDIENTES o CONFIRMADOS o EN ESPERA
        val turnos = if(esHistorico){
            turnoService.obtenerTurnosHistoricosDelCliente(maybeCliente.get(),pageable)
        }else{
            turnoService.obtenerTurnosEnEsperaOPendientesOConfirmadosDelCliente(maybeCliente.get(),pageable)
        }
        return turnos.map { convertTurnoToTurnoConDireccionDTO(it) }
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

    @PostMapping("/confirmar")
    fun confirmarTurno(@Valid @RequestBody turnoDTO: TurnoDTO):Turno {
        return turnoService.confirmarTurno(this.validarIdTurnoPeluquero(turnoDTO.idTurno))
    }

    @PostMapping("/finalizar")
    fun finalizarTurno(@Valid @RequestBody turnoDTO: TurnoDTO): Boolean {
        return turnoService.finalizarTurno(this.validarIdTurnoPeluquero(turnoDTO.idTurno)).peluquero.getEstaDisponible()
    }

    @PostMapping("/cancelar")
    fun cancelarTurno(@Valid @RequestBody turnoDTO: TurnoDTO) : Turno {
        val turno = this.validarIdTurnoCliente(turnoDTO.idTurno)
        if(! turno.getEstaEsperando())
            throw TurnoNoPuedeSerCancelado()
        return turnoService.cancelarTurno(turno)
    }

    @PostMapping("/calificar")
    fun calificarTurno(@Valid @RequestBody calificacionTurnoDTO: CalificacionTurnoDTO):Turno {
        val turno = this.validarIdTurnoCliente(calificacionTurnoDTO.idTurno)

        if(!turno.getEstaFinalizado())
            throw TurnoNoSePuedeCalificar()

        if(turno.puntaje > 0)
            throw TurnoYaCalificadoException()

        return turnoService.calificarTurno(turno, calificacionTurnoDTO.puntaje)
    }

    private fun getMaybePeluqueroByJWT(): Optional<Peluquero> {
        val emailPeluquero = rolService.getEmail()
        return peluqueroService.getByEmail(emailPeluquero)
    }

    private fun getMaybeClienteByJWT(): Optional<Cliente> {
        val emailCliente = rolService.getEmail()
        return clienteService.getByEmail(emailCliente)
    }

    private fun validarIdTurnoCliente(idTurno: Long) : Turno {
        val maybeTurno = turnoService.get(idTurno)
        if(! maybeTurno.isPresent)
            throw TurnoNoExisteException(idTurno)

        if(maybeTurno.get().getClienteId() != getMaybeClienteByJWT().get().id)
            throw Unauthorized("No tiene acceso a este recurso")

        return maybeTurno.get()
    }

    private fun validarIdTurnoPeluquero(idTurno: Long) : Turno {
        val maybePeluquero = getMaybePeluqueroByJWT()
        val maybeTurno = turnoService.get(idTurno)
        if(! maybeTurno.isPresent)
            throw TurnoNoExisteException(idTurno)

        if(maybeTurno.get().getPeluqueroId()!! != maybePeluquero.get().id)
            throw Unauthorized("No tiene acceso a este recurso")

        return maybeTurno.get()
    }

    private fun convertTurnoToTurnoConDireccionDTO(turno:Turno) : TurnoConDireccionDTO {
        return TurnoConDireccionDTO.Builder().withTurno(turno).build()
    }
}