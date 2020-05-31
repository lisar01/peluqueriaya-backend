package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.Cliente
import java.util.*

interface ClienteService : GenericService<Cliente> {
    fun getByEmail(email: String): Optional<Cliente>
}