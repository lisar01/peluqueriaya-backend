package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import org.hibernate.validator.constraints.URL
import java.math.BigDecimal
import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class PeluqueroDTO(
        @field:Size(min = 2, max=30, message = "{nombre.largo}")
        val nombre: String,
        @field:URL(message = "{logo.invalido}")
        val logo: String,
        @field:Email(message = "{email.invalido}")
        val emailOpcional: String,
        @field:Size(min = 20, max=200, message = "{descripcion.largo}")
        val descripcion: String,
        val ubicacion: Ubicacion,
        @field:Size(min = 1, message = "{tipos.min}")
        val tipos: Set<PeluqueroType>,
        @field:Min(value = 1, message = "{distanciaMax.largo}")
        @field:Max(value = 25, message = "{distanciaMax.largo}")
        val distanciaMax: BigDecimal? = 20.toBigDecimal(),
        @field:Min(value = 70, message = "{corteMin.largo}")
        @field:Max(value = 300, message = "{corteMin.largo}")
        val corteMin: BigDecimal? = 70.toBigDecimal()) {

        fun toPeluquero(email: String): Peluquero =
                Peluquero.Builder()
                        .withNombre(nombre)
                        .withLogo(logo)
                        .withEmail(email)
                        .withEmailOpcional(emailOpcional)
                        .withDescripcion(descripcion)
                        .withDistanciaMax(distanciaMax!!)
                        .withCorteMin(corteMin!!)
                        .withUbicacion(ubicacion)
                        .withTipos(tipos.toMutableSet())
                        .build()

}
