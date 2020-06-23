package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.Email
import javax.validation.constraints.Pattern

class ClienteEditarDatosDTO(
        @field:URL(message = "{imgPerfil.invalido}")
        var imgPerfil: String?,
        @field:Length(min = 2, max=30, message = "{nombre.largo}")
        var nombre:String?,
        @field:Length(min = 2, max=30, message = "{apellido.largo}")
        var apellido:String?,
        @field:Email(message = "{email.invalido}")
        var emailOpcional:String?,
        @field:Pattern(regexp = "^(?:(?:00)?549?)?0?(?:11|[2368]\\d)(?:(?=\\d{0,2}15)\\d{2})??\\d{8}$",
                message = "{nroTelefono.invalido}")
        var nroTelefono:String?,
        var ubicacion: Ubicacion?
) {
    fun editarDatosCliente(cliente: Cliente):Cliente {
        cliente.imgPerfil = imgPerfil?:cliente.imgPerfil
        cliente.nombre = nombre?:cliente.nombre
        cliente.apellido = apellido?:cliente.apellido
        cliente.emailOpcional = emailOpcional?:cliente.emailOpcional
        cliente.nroTelefono = nroTelefono?:cliente.nroTelefono
        cliente.ubicacion = ubicacion?:cliente.ubicacion

        return cliente
    }

}