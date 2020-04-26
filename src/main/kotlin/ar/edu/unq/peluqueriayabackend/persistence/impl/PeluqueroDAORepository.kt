package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.service.geodistance.GeoDistanceServiceApi
import ar.edu.unq.peluqueriayabackend.service.geodistance.impl.GeoDistanceServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PeluqueroDAORepository(@Autowired val peluqueroRepository: PeluqueroRepository):PeluqueroDAO{

    override fun get(id: Int): Optional<Peluquero> {
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
    override fun delete(id: Int) {
        val peluqueroDeshabilitado = get(id).get()
        peluqueroDeshabilitado.estado = PeluqueroState.DESHABILITADO
        save(peluqueroDeshabilitado)
    }

    override fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(distanciaEnKm: Double,latitude:Double, longitude:Double,pageable:Pageable): Page<Peluquero> {
        return peluqueroRepository.findAllInRangeAtCoordinates(
                distanciaEnKm,
                latitude,
                longitude,
                pageable)
    }

    override fun buscarPeluquerosConNombreOTipoYQueEstenDentroDelRadioEnKmDeLaUbicacion(nombre:String, tipo:String, distanciaEnKm: Double,latitude: Double,longitude: Double,pageable: Pageable): Page<Peluquero> {
        return peluqueroRepository.findAllInRangeAtCoordinatesAndLikeNameOrWithTipos(
                distanciaEnKm,
                latitude,
                longitude,
                nombre,
                tipo,
                pageable
        )
    }
}