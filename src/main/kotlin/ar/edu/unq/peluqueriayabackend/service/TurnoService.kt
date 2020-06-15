package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TurnoService : GenericService<Turno> {
    fun pedirTurno(cliente: Cliente, peluquero: Peluquero, serviciosSolicitadosInfo: List<ServicioInfo>, ubicacion: Ubicacion):Turno
    fun confirmarTurno(turno: Turno): Turno
    fun finalizarTurno(turno: Turno): Turno
    fun cancelarTurno(turno: Turno) : Turno
    fun obtenerTodosLosTurnosDelPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno>
    fun obtenerTurnosHistoricosDelPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno>
    fun obtenerTurnosPendientesOConfirmadosDelPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno>
    fun puntuacionPromedioDelPeluquero(peluquero: Peluquero): Double
    fun obtenerTurnosHistoricosDelCliente(cliente: Cliente, pageable: Pageable): Page<Turno>
    fun obtenerTurnosEnEsperaOPendientesOConfirmadosDelCliente(cliente: Cliente, pageable: Pageable): Page<Turno>
    fun calificarTurno(turno: Turno, puntaje: Int): Turno
}