package ar.edu.unq.peluqueriayabackend.controller.dtos.peluquero

import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import java.math.BigDecimal

class PeluqueroPerfilDTO(
        val id: Long?,
        val logo: String,
        val nombre: String,
        val corteMin: BigDecimal,
        val distanciaMax: BigDecimal,
        val emailOpcional: String,
        val ubicacion: Ubicacion,
        val descripcion: String,
        val tipos: MutableSet<PeluqueroType>,
        val estaDesconectado: Boolean,
        val puntuacion: Double? = null)

