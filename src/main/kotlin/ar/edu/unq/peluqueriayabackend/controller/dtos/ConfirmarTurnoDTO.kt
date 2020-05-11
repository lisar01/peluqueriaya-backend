package ar.edu.unq.peluqueriayabackend.controller.dtos

import javax.validation.constraints.NotNull

data class ConfirmarTurnoDTO(
        @NotNull val peluqueroId: Long,
        @NotNull val turnoId: Long
)