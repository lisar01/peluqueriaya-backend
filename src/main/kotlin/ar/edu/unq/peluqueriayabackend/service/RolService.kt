package ar.edu.unq.peluqueriayabackend.service

import ar.edu.unq.peluqueriayabackend.controller.dtos.RolesDTO


interface RolService {
    fun getEmail(): String
    fun tieneRolCliente(): Boolean
    fun tieneRolPeluquero(): Boolean
    fun getRolesByEmail(email: String): RolesDTO
}