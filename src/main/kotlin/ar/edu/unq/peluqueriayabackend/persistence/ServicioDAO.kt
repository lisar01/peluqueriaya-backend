package ar.edu.unq.peluqueriayabackend.persistence

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio

interface ServicioDAO : GenericDAO<Servicio> {
    fun serviciosDelPeluquero(peluquero: Peluquero): Collection<Servicio>

}