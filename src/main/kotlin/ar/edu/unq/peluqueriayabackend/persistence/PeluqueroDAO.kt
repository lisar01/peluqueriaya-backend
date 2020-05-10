package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PeluqueroDAO:GenericDAO<Peluquero> {
    fun findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(distanciaMaxima: Double, longitud: Double, latitud: Double, nombre: String?, tipos: List<PeluqueroType>?, tipoDeServicio: ServicioType?, pageable: Pageable): Page<Peluquero>
}