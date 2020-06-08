package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface TurnoDAO : GenericDAO<Turno> {
    fun peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean
    fun peluqueroPoseeAlgunTurnoConfirmado(peluquero: Peluquero): Boolean
    fun peluqueroPoseeCantidadDeTurnosEnEsperaMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean
    fun findTurnoEnEsperaMasAntiguoDelPeluquero(peluquero: Peluquero): Optional<Turno>
    fun findAllByEstadoPendienteOEspera(peluquero: Peluquero): List<Turno>
    fun findAllConPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno>
    fun findAllConPeluqueroYEstadoFinalizado(peluquero: Peluquero, pageable: Pageable): Page<Turno>
    fun findAllConPeluqueroYEstadoPendientesOConfirmados(peluquero: Peluquero, pageable: Pageable): Page<Turno>
    fun obtenerPromedioPuntuacionDeLosTurnosConPeluquero(peluquero: Peluquero): Double
}