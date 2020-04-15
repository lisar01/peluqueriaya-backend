package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio

interface ServicioService: GenericService<Servicio> {

    fun serviciosDelPeluquero(peluquero:Peluquero):Collection<Servicio>
}