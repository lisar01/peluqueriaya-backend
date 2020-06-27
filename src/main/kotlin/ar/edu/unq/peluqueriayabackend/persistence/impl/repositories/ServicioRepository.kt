package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ServicioRepository : JpaRepository<Servicio, Long> {

    fun findAllByPeluquero(peluquero: Peluquero): Collection<Servicio>
    fun findByPeluqueroEmail(email: String): List<Servicio>
}