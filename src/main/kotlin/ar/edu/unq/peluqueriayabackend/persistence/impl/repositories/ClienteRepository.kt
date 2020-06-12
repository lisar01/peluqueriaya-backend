package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.controller.dtos.ClientePerfilDTO
import ar.edu.unq.peluqueriayabackend.model.Cliente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClienteRepository : JpaRepository<Cliente, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String) : Optional<Cliente>
    fun queryByEmail(email: String): ClientePerfilDTO?
}