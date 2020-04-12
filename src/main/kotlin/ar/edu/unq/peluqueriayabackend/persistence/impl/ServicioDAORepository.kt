package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.persistence.ServicioDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ServicioRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

class ServicioDAORepository(@Autowired val servicioRepository: ServicioRepository) : ServicioDAO{

    override fun get(id: Int): Optional<Servicio> {
        return servicioRepository.findById(id)
    }

    override fun getAll(): Collection<Servicio> {
        return servicioRepository.findAll()
    }

    override fun save(t: Servicio): Servicio {
        return servicioRepository.save(t)
    }

    override fun update(t: Servicio): Servicio {
        return servicioRepository.save(t)
    }

    override fun delete(id: Int) {
        return servicioRepository.deleteById(id)
    }

    override fun serviciosDelPeluquero(peluquero: Peluquero): Collection<Servicio> {
        return servicioRepository.findAllByPeluquero(peluquero)
    }
}