package ar.edu.unq.peluqueriayabackend.controller.dtos

import javax.validation.constraints.NotNull

data class SolicitudTurnoDTO (
        @NotNull val idPeluquero:Long,
        @NotNull val idCliente:Long,
        val serviciosSolicitadosId: MutableList<Long> = mutableListOf()
)