package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.dtos.PerfilesDTO


interface RolService {
    fun getEmail(): String
    fun tieneRolCliente(): Boolean
    fun tieneRolPeluquero(): Boolean
    fun getPerfiles(email: String): PerfilesDTO
}