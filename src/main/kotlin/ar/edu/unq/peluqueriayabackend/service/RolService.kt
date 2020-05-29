package ar.edu.unq.peluqueriayabackend.service


interface RolService {
    fun getEmail(): String
    fun tieneRolCliente(): Boolean
    fun tieneRolPeluquero(): Boolean
}