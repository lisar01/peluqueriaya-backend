package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno

interface TurnoDAO : GenericDAO<Turno> {
    fun peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean
    fun peluqueroPoseeAlgunTurnoConfirmado(peluquero: Peluquero): Boolean
}