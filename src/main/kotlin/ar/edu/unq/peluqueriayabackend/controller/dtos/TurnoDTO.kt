package ar.edu.unq.peluqueriayabackend.controller.dtos

import javax.validation.constraints.NotNull

data class TurnoDTO(
        @NotNull val idTurno: Long
)