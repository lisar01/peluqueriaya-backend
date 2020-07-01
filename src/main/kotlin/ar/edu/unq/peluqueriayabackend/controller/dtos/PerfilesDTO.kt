package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.controller.dtos.peluquero.PeluqueroPerfilDTO
import ar.edu.unq.peluqueriayabackend.model.Cliente
import java.util.*

data class PerfilesDTO(val cliente: Optional<Cliente>, val peluquero: PeluqueroPerfilDTO?)
