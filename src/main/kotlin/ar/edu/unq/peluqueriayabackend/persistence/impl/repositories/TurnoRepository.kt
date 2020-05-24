package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TurnoRepository : JpaRepository<Turno, Long> {

    @Query(value = "SELECT COUNT(t) >= ?2 FROM Turno t WHERE t.peluquero = ?1 AND (t.estado = 0 OR t.estado = 1)")
    fun peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero : Peluquero, valor : Long):Boolean

    @Query(value = "SELECT COUNT(t) > 0 FROM Turno t WHERE t.peluquero = ?1 AND t.estado = 1")
    fun peluqueroPoseeAlgunTurnoConfirmado(peluquero: Peluquero): Boolean

    @Query("SELECT COUNT(t) >= ?2 FROM Turno t WHERE t.peluquero = ?1 AND t.estado = 4")
    fun peluqueroPoseeCantidadDeTurnosEnEsperaMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean

    @Query("SELECT t FROM Turno t WHERE t.peluquero = ?1 AND t.estado = 4 ORDER BY t.fechaInicio ASC")
    fun getPrimerTurnoEnEsperaDelPeluqueroOrdenadoPor(peluquero: Peluquero): Optional<Turno>

    @Query("SELECT t FROM Turno t WHERE t.peluquero = ?1 AND (t.estado = 0 OR t.estado = 4)")
    fun findAllByEstadoPendienteOEspera(peluquero: Peluquero): List<Turno>

}