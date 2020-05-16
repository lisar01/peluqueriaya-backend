package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.ServicioInfo
import ar.edu.unq.peluqueriayabackend.model.Turno

interface TurnoService : GenericService<Turno> {
    fun pedirTurno(cliente: Cliente, peluquero: Peluquero, serviciosSolicitadosInfo: List<ServicioInfo>):Turno
    fun confirmarTurno(turno: Turno): Turno
    fun finalizarTurno(turno: Turno): Turno
}