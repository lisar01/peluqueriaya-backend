package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.service.geodistance.GeoDistanceServiceApi
import ar.edu.unq.peluqueriayabackend.service.geodistance.impl.GeoDistanceServiceImpl
import org.springframework.beans.factory.annotation.Autowired
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

    override fun delete(id: Int) {
        //TODO PONER UN FLAG DE SI EL PELUQUERO SE ELIMINO O NO
    }

    override fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(ubicacion: Ubicacion, distanciaEnKm: Double): List<Peluquero> {

        val geoDistance:GeoDistanceServiceApi = GeoDistanceServiceImpl()

        return peluqueroRepository.findAll().filter { p -> geoDistance.distanciaUbicacionesEnKM(ubicacion,p.ubicacion) <= distanciaEnKm }
    }
}