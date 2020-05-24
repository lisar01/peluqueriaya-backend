package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.*

interface TurnoService : GenericService<Turno> {
    fun pedirTurno(cliente: Cliente, peluquero: Peluquero, serviciosSolicitadosInfo: List<ServicioInfo>, ubicacion: Ubicacion):Turno
    fun confirmarTurno(turno: Turno): Turno
    fun finalizarTurno(turno: Turno): Turno
    fun cancelarTurno(turno: Turno) : Turno
}