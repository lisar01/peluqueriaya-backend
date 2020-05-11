package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TurnoRepository : JpaRepository<Turno, Long> {

    @Query(value = "SELECT COUNT(t) >= ?2 FROM Turno t WHERE t.peluquero = ?1")
    fun peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero : Peluquero, valor : Long):Boolean

}