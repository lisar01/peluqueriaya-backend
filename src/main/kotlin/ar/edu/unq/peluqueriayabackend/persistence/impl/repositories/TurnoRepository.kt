package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.model.TurnoState
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TurnoRepository : JpaRepository<Turno, Long> {

    @Query(value = "SELECT COUNT(t) >= ?2 FROM Turno t WHERE t.peluquero = ?1 AND (t.estado = 0 OR t.estado = 1)")
    fun peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero : Peluquero, valor : Long):Boolean

    @Query(value = "SELECT COUNT(t) > 0 FROM Turno t WHERE t.peluquero = ?1 AND t.estado = 1")
    fun peluqueroPoseeAlgunTurnoConfirmado(peluquero: Peluquero): Boolean

    @Query("SELECT COUNT(t) >= ?2 FROM Turno t WHERE t.peluquero = ?1 AND t.estado = 4")
    fun peluqueroPoseeCantidadDeTurnosEnEsperaMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean

    @Query("SELECT t FROM Turno t WHERE t.peluquero = ?1 AND t.estado = 4")
    fun findTurnosEnEsperaDelPeluqueroOrdenadoPor(peluquero: Peluquero, pageable: Pageable): Page<Turno>

    @Query("SELECT t FROM Turno t WHERE t.peluquero = ?1 AND (t.estado = 0 OR t.estado = 4)")
    fun findAllByEstadoPendienteOEspera(peluquero: Peluquero): List<Turno>

    fun findAllByPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno>

    @Query("SELECT t FROM Turno t WHERE t.peluquero = :paramPeluquero AND t.estado = :paramEstado")
    fun findAllByPeluqueroAndEstadoTurno(
            @Param("paramPeluquero") peluquero: Peluquero,
            @Param("paramEstado") estado: TurnoState,
            pageable: Pageable): Page<Turno>

    @Query("SELECT t FROM Turno t WHERE t.peluquero = :paramPeluquero AND (t.estado = 0 OR t.estado = 1)")
    fun findAllByPeluqueroAndEstadoTurnoConfirmadoOPendiente(
            @Param("paramPeluquero") peluquero: Peluquero,
            pageable: Pageable
    ): Page<Turno>

    @Query("SELECT AVG(t.puntaje) FROM Turno t WHERE t.peluquero = :paramPeluquero AND t.estado = 2 AND t.puntaje > 0")
    fun obtenerPromedioPuntuacionDeLosTurnosConPeluquero(@Param("paramPeluquero") peluquero: Peluquero): Double

    @Query("SELECT AVG(t.puntaje) FROM Turno t WHERE t.peluquero.email = :emailPeluquero AND t.estado = 2 AND t.puntaje > 0")
    fun getPuntuacionDePeluquero(@Param("emailPeluquero") email: String): Double?

    @Query("SELECT t FROM Turno t WHERE t.cliente = :paramCliente AND (t.estado = 2 OR t.estado = 3)")
    fun findAllConClienteYEstadoFinalizadoOCancelado(
            @Param("paramCliente") cliente: Cliente,
            pageable: Pageable
    ): Page<Turno>

    @Query("SELECT t FROM Turno t WHERE t.cliente = :paramCliente AND (t.estado = 0 OR t.estado = 1 OR t.estado = 4)")
    fun findAllConClienteYEstadoEnEsperaOPendienteOConfirmado(
            @Param("paramCliente") cliente: Cliente,
            pageable: Pageable
    ): Page<Turno>
}