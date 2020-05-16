package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.persistence.ClienteDAO
import ar.edu.unq.peluqueriayabackend.service.ClienteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class ClienteServiceImpl(@Autowired val clienteDAO: ClienteDAO) : ClienteService {

    override fun get(id: Long): Optional<Cliente> {
        return clienteDAO.get(id)
    }

    override fun getAll(): Collection<Cliente> {
        return clienteDAO.getAll()
    }

    @Transactional
    override fun save(t: Cliente): Cliente {
        return clienteDAO.save(t)
    }

    @Transactional
    override fun update(t: Cliente): Cliente {
        return clienteDAO.update(t)
    }
}