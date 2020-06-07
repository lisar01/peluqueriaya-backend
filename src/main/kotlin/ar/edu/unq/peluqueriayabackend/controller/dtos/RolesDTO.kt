package ar.edu.unq.peluqueriayabackend.controller.dtos

class RolesDTO(esCliente: Boolean, esPeluquero: Boolean) {
    private fun aRolDTO(esRol: Boolean): RolType =
            if (esRol) RolType.REGISTRADO else RolType.VISITANTE

    val cliente: RolType = aRolDTO(esCliente)
    val peluquero: RolType = aRolDTO(esPeluquero)

}
