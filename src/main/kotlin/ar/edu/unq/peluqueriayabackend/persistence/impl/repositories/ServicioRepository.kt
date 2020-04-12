package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import org.springframework.data.jpa.repository.JpaRepository

interface ServicioRepository : JpaRepository<Servicio, Int> {

    fun findAllByPeluquero(peluquero: Peluquero): Collection<Servicio>
}