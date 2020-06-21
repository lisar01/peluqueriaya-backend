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

class PeluqueroEditarDatosDTO(
        @field:Size(min = 10, max=60, message = "{nombre.size}")
        val nombre: String?,
        @field:URL(message = "{logo.invalido}")
        val logo: String?,
        @field:Email(message = "{email.invalido}")
        val emailOpcional: String?,
        @field:Size(min = 20, max=200, message = "{descripcion.largo}")
        val descripcion: String?,
        @field:Size(min = 1, message = "{tipos.min}")
        val tipos: MutableSet<PeluqueroType>?,
        @field:Min(value = 1, message = "{distanciaMax.largo}")
        @field:Max(value = 25, message = "{distanciaMax.largo}")
        val distanciaMax: BigDecimal?,
        @field:Min(value = 70, message = "{corteMin.largo}")
        @field:Max(value = 300, message = "{corteMin.largo}")
        val corteMin: BigDecimal?,
        val ubicacion: Ubicacion?
) {
    fun editarDatosPeluquero(peluquero: Peluquero) : Peluquero {
        peluquero.nombre = nombre?:peluquero.nombre
        peluquero.descripcion = descripcion?:peluquero.descripcion
        peluquero.emailOpcional = emailOpcional?:peluquero.emailOpcional
        peluquero.logo = logo?:peluquero.logo
        peluquero.corteMin = corteMin?:peluquero.corteMin
        peluquero.distanciaMax = distanciaMax?:peluquero.distanciaMax
        peluquero.tipos = tipos?:peluquero.tipos
        peluquero.ubicacion = ubicacion?:peluquero.ubicacion
        return peluquero
    }
}