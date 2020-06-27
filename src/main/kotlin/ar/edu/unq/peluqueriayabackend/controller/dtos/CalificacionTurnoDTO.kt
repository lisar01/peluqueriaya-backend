package ar.edu.unq.peluqueriayabackend.controller.dtos

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class CalificacionTurnoDTO (
        @NotNull val idTurno: Long,
        @field:Min(value = 1, message = "{puntaje.min}")
        @field:Max(value = 5, message = "{puntaje.max}")
        val puntaje:Int
)