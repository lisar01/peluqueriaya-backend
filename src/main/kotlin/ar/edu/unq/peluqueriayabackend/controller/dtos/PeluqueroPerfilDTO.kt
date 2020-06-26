package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import java.math.BigDecimal

class PeluqueroPerfilDTO(
        peluqueroPerfilSinPuntuacionDTO: PeluqueroPerfilSinPuntuacionDTO,
        val puntuacion: Double?) {

    val id: Long? = peluqueroPerfilSinPuntuacionDTO.id
    val logo: String = peluqueroPerfilSinPuntuacionDTO.logo
    val nombre: String = peluqueroPerfilSinPuntuacionDTO.nombre
    val corteMin: BigDecimal = peluqueroPerfilSinPuntuacionDTO.corteMin
    val distanciaMax: BigDecimal = peluqueroPerfilSinPuntuacionDTO.distanciaMax
    val emailOpcional: String = peluqueroPerfilSinPuntuacionDTO.emailOpcional
    val ubicacion: Ubicacion = peluqueroPerfilSinPuntuacionDTO.ubicacion
    val estado: PeluqueroState = peluqueroPerfilSinPuntuacionDTO.estado
    val descripcion: String = peluqueroPerfilSinPuntuacionDTO.descripcion
    val tipos: MutableSet<PeluqueroType> = peluqueroPerfilSinPuntuacionDTO.tipos
    val estaDisponible: Boolean = peluqueroPerfilSinPuntuacionDTO.getEstaDisponible()
    val estaDesconectado: Boolean = peluqueroPerfilSinPuntuacionDTO.getEstaDesconectado()

}

