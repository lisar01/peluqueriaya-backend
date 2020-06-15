package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.dtos.ServicioDTO
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio


interface ServicioService: GenericService<Servicio> {
    fun serviciosDelPeluquero(peluquero:Peluquero):Collection<Servicio>
    fun guardar(servicioDTO: ServicioDTO, email: String)
}