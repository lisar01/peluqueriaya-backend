package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import javax.validation.constraints.NotNull

data class SolicitudTurnoDTO (
        @NotNull val idPeluquero:Long,
        @NotNull val ubicacion: Ubicacion,
        val serviciosSolicitadosId: MutableList<Long> = mutableListOf()
)