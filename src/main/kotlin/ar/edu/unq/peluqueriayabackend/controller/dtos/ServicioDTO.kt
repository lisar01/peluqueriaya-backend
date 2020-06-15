package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import java.math.BigDecimal
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class ServicioDTO(
        @field:Size(min = 2, max=30, message = "{nombre.largo}")
        var nombre: String,
        @field:Size(min = 1, message = "{tiposServicio.min}")
        var tipos: MutableSet<ServicioType>,
        @field:Min(value = 100, message = "{precio.largo}")
        @field:Max(value = 5000, message = "{precio.largo}")
        var precio: BigDecimal) {

    fun toServicio(peluquero: Peluquero) = Servicio.Builder()
            .withNombre(nombre)
            .withPrecio(precio)
            .withTipos(tipos)
            .withPeluquero(peluquero)
            .build()

}