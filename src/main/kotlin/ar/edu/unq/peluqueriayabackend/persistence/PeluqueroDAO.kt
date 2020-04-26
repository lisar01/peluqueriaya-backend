package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PeluqueroDAO:GenericDAO<Peluquero> {

    fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(
            distanciaEnKm: Double,latitude:Double, longitude:Double,pageable: Pageable)
            : Page<Peluquero>

    fun buscarPeluquerosConNombreOTipoYQueEstenDentroDelRadioEnKmDeLaUbicacion(
            nombre:String, tipo:String, distanciaEnKm: Double,latitude: Double,longitude: Double,pageable: Pageable)
            : Page<Peluquero>
}