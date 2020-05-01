package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PeluqueroService:GenericService<Peluquero> {
    fun buscarPeluquerosCercanos(ubicacion: Ubicacion, pageable: Pageable): Page<Peluquero>

    fun buscarPeluquerosCercanosPorNombreOTipo(ubicacion: Ubicacion, nombreOTipo:String, pageable: Pageable):Page<Peluquero>
}
