package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Cliente
import java.util.*

interface ClienteDAO : GenericDAO<Cliente> {
    fun getByEmail(email: String): Optional<Cliente>
}