package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.controller.dtos.ServicioDTO
import ar.edu.unq.peluqueriayabackend.exception.ServicioNoExisteException
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.ServicioDAO
import ar.edu.unq.peluqueriayabackend.service.ServicioService
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class ServicioServiceImpl(val servicioDAO:ServicioDAO, val peluqueroDAO: PeluqueroDAO):ServicioService {

    override fun get(id: Long): Optional<Servicio> {
        return servicioDAO.get(id)
    }

    override fun getAll(): Collection<Servicio> {
        return servicioDAO.getAll()
    }

    @Transactional
    override fun save(t: Servicio): Servicio {
        return servicioDAO.save(t)
    }

    @Transactional
    override fun update(t: Servicio): Servicio {
        return servicioDAO.update(t)
    }

    override fun serviciosDelPeluquero(peluquero: Peluquero): Collection<Servicio> {
        return servicioDAO.serviciosDelPeluquero(peluquero)
    }

    @Transactional
    override fun guardar(servicioDTO: ServicioDTO, email: String) {
        servicioDAO.save(servicioDTO.toServicio(peluqueroDAO.getByEmail(email).get()))
    }

    @Transactional
    override fun borrar(id: Long, emailPeluquero: String): Long {
        val peluquero = peluqueroDAO.getByEmail(emailPeluquero).get()
        if (peluquero.eliminarServicio(id)) {
            peluqueroDAO.save(peluquero)
            return id
        }
        else {
            throw ServicioNoExisteException()
        }
    }
}