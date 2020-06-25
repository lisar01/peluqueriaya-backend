package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import java.math.BigDecimal

interface PeluqueroPerfilSinPuntuacionDTO {
    var id: Long?
    var logo: String
    var nombre: String
    var corteMin: BigDecimal
    var distanciaMax: BigDecimal
    var emailOpcional: String
    var ubicacion: Ubicacion
    var estado: PeluqueroState
    var descripcion: String
    var tipos: MutableSet<PeluqueroType>
    fun getEstaDisponible() : Boolean
    fun getEstaDesconectado() : Boolean
}