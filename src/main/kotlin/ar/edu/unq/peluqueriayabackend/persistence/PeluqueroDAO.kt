package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion

interface PeluqueroDAO:GenericDAO<Peluquero> {

    fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(ubicacion: Ubicacion, distanciaEnKm: Double): List<Peluquero>
}