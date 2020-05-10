package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    @Transactional
    override fun buscar(ubicacion: Ubicacion, filtro: Filtro?, pageable: Pageable): Page<Peluquero> {
                return peluqueroDAO.findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(5.3,
                ubicacion.getLongitudeAsDouble(), ubicacion.getLatitudeAsDouble(),
                filtro?.nombre, filtro?.tipos, filtro?.tipoDeServicio, pageable)
    }
}