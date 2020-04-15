package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion

interface PeluqueroService:GenericService<Peluquero> {
    fun buscarPeluquerosCercanos(ubicacion: Ubicacion): List<Peluquero>
}
