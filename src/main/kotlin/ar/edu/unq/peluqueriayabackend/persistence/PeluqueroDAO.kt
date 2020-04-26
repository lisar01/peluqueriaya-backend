package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Ubicacion

interface PeluqueroDAO:GenericDAO<Peluquero> {

    fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(distanciaEnKm: Double,latitude:Double, longitude:Double): List<Peluquero>
}