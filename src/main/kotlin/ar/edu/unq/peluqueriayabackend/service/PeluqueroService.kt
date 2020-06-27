package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*


interface PeluqueroService:GenericService<Peluquero> {
    fun buscar(ubicacion: Ubicacion, filtro: Filtro?, pageable: Pageable): Page<Peluquero>
    fun desconectar(peluquero: Peluquero):Peluquero
    fun conectar(peluquero: Peluquero): Peluquero
    fun getByEmail(emailPeluquero: String): Optional<Peluquero>
    fun getServiciosByEmail(email: String): List<Servicio>
}
