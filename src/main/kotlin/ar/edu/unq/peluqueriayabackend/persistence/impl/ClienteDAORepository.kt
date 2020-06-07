package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.model.ClienteState
import ar.edu.unq.peluqueriayabackend.persistence.ClienteDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ClienteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClienteDAORepository(@Autowired val clienteRepository: ClienteRepository) : ClienteDAO {

    override fun getByEmail(email: String): Optional<Cliente> = clienteRepository.findByEmail(email)

    override fun get(id: Long): Optional<Cliente> {
        return clienteRepository.findById(id)
    }

    override fun getAll(): Collection<Cliente> {
        return clienteRepository.findAll()
    }

    override fun save(t: Cliente): Cliente {
        return clienteRepository.save(t)
    }

    override fun update(t: Cliente): Cliente {
        return clienteRepository.save(t)
    }

    override fun delete(id: Long) {
        val clienteADeshabilitar = get(id).get()
        clienteADeshabilitar.estado = ClienteState.DEHABILITADO
        update(clienteADeshabilitar)
    }
}