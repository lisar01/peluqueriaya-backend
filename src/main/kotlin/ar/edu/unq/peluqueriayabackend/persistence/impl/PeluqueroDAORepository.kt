package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PeluqueroDAORepository(@Autowired val peluqueroRepository: PeluqueroRepository):PeluqueroDAO{

    override fun get(id: Long): Optional<Peluquero> {
        return peluqueroRepository.findById(id)
    }

    override fun getAll(): Collection<Peluquero> {
        return peluqueroRepository.findAll()
    }

    override fun save(t: Peluquero): Peluquero {
        return peluqueroRepository.save(t)
    }

    override fun update(t: Peluquero): Peluquero {
        return save(t)
    }

    // Solo setea el estado en deshabilitado (No borra nada)
    override fun delete(id: Long) {
        val peluqueroDeshabilitado = get(id).get()
        peluqueroDeshabilitado.estado = PeluqueroState.DESHABILITADO
        save(peluqueroDeshabilitado)
    }

    override fun findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(distanciaMaxima: Double, longitud: Double, latitud: Double, nombre: String?, tipos: List<PeluqueroType>?, tipoDeServicio: ServicioType?, pageable: Pageable): Page<Peluquero> {
        return peluqueroRepository.findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(distanciaMaxima, longitud, latitud, nombre, tipos, tipoDeServicio, pageable)
    }
}