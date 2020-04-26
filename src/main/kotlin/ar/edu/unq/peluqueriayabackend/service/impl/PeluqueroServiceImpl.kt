package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class PeluqueroServiceImpl(@Autowired val peluqueroDAO: PeluqueroDAO): PeluqueroService {

    override fun get(id: Int): Optional<Peluquero> {
        return peluqueroDAO.get(id)
    }

    override fun getAll(): Collection<Peluquero> {
        return peluqueroDAO.getAll()
    }

    @Transactional
    override fun save(t: Peluquero): Peluquero {
        return peluqueroDAO.save(t)
    }

    @Transactional
    override fun update(t: Peluquero): Peluquero {
        return peluqueroDAO.update(t)
    }

    override fun buscarPeluquerosCercanos(ubicacion: Ubicacion): List<Peluquero> {
        return peluqueroDAO.buscarPeluquerosEnUbicacionDentroDelRadioEnKm(
                5.3,
                ubicacion.getLatitudeAsDouble(),
                ubicacion.getLongitudeAsDouble())
    }
}