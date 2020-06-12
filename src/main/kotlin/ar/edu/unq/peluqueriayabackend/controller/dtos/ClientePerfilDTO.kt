package ar.edu.unq.peluqueriayabackend.controller.dtos

import org.springframework.beans.factory.annotation.Value

@Suppress("ELValidationInJSP", "SpringElInspection")
interface ClientePerfilDTO {
    val imgPerfil: String
    @Value("#{target.nombre + ' ' + target.apellido}")
    fun getFullName(): String
}