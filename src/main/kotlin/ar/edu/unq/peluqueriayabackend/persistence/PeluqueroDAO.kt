package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PeluqueroDAO:GenericDAO<Peluquero> {

    fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(
            distanciaEnKm: Double,ubicacion: Ubicacion,pageable: Pageable)
            : Page<Peluquero>

    fun buscarPeluquerosConNombreOTipoYQueEstenDentroDelRadioEnKmDeLaUbicacion(
            nombreOTipo:String, distanciaEnKm: Double,ubicacion: Ubicacion,pageable: Pageable)
            : Page<Peluquero>

    fun buscarPeluquerosPorTipoDeServicioYQueEstenDentroDelRadioEnKmDeLaUbicacion(
            tipoDeServicio: ServicioType, distanciaEnKm: Double, ubicacion: Ubicacion, pageable: Pageable)
            : Page<Peluquero>
}