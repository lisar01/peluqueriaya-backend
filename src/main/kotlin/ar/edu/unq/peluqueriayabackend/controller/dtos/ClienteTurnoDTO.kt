package ar.edu.unq.peluqueriayabackend.controller.dtos

import javax.validation.constraints.NotNull

class ClienteTurnoDTO(
    @NotNull val clienteId :Long,
    @NotNull val turnoId: Long
)