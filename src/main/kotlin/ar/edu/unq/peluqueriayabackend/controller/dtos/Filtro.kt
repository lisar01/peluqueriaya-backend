package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import javax.validation.constraints.Size

class Filtro(
        @field:Size(min = 4, max = 20, message = "{nombre.tamanio}")
        val nombre: String?,
        val tipos: List<PeluqueroType>?,
        val tipoDeServicio: ServicioType?)
