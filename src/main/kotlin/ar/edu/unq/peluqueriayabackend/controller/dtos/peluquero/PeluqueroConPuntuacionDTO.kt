package ar.edu.unq.peluqueriayabackend.controller.dtos.peluquero

import ar.edu.unq.peluqueriayabackend.model.*
import java.math.BigDecimal
import java.time.LocalDateTime

class PeluqueroConPuntuacionDTO(
        var logo: String,
        var nombre: String,
        var corteMin: BigDecimal,
        var distanciaMax: BigDecimal,
        var email: String,
        var emailOpcional:String,
        var ubicacion: Ubicacion,
        var estado: PeluqueroState,
        var descripcion: String,
        var tipos: MutableSet<PeluqueroType>,
        var servicios:MutableList<Servicio>,
        var ultimoLogin: LocalDateTime,
        var vecesDeshabilitado: Int,
        var fechaDeshabilitacion: LocalDateTime?,
        var id: Long?,
        var puntuacionPromedio: Double
)
{

    fun getTieneTurnosEnEspera() : Boolean = estado.tieneTurnosEnEspera()

    fun getEstaDisponible() : Boolean = estado.estaDisponible()

    fun getEstaOcupado() : Boolean = estado.estaOcupado()

    fun getEstaDesconectado() : Boolean = estado.estaDesconectado()

    fun getEstaDeshabilitado() : Boolean = estado.estaDeshabilitado()

    class Builder(
            var logo: String = "",
            var nombre: String = "",
            var corteMin: BigDecimal = BigDecimal.ZERO,
            var distanciaMax: BigDecimal = BigDecimal.ZERO,
            var email: String = "",
            var emailOpcional:String = "",
            var ubicacion: Ubicacion = Ubicacion("","",""),
            var estado: PeluqueroState = PeluqueroState.DISPONIBLE,
            var descripcion: String = "",
            var tipos: MutableSet<PeluqueroType> = mutableSetOf(),
            var servicios:MutableList<Servicio> = mutableListOf(),
            var ultimoLogin: LocalDateTime = LocalDateTime.now(),
            var vecesDeshabilitado: Int = 0,
            var fechaDeshabilitacion: LocalDateTime? = null,
            var id: Long? = null,
            var puntuacionPromedio: Double = 0.toDouble()
    ){

        fun build(): PeluqueroConPuntuacionDTO {
            return PeluqueroConPuntuacionDTO(logo, nombre, corteMin,
                    distanciaMax, email, emailOpcional, ubicacion,
                    estado, descripcion, tipos, servicios, ultimoLogin,
                    vecesDeshabilitado, fechaDeshabilitacion, id, puntuacionPromedio)
        }

        fun withPuntuacionPromedio(puntuacionPromedio:Double) = apply { this.puntuacionPromedio = puntuacionPromedio }

        fun withPeluquero(peluquero:Peluquero) = apply {
            logo = peluquero.logo
            nombre = peluquero.nombre
            corteMin = peluquero.corteMin
            distanciaMax = peluquero.distanciaMax
            email = peluquero.email
            emailOpcional = peluquero.emailOpcional
            ubicacion = peluquero.ubicacion
            estado = peluquero.estado
            descripcion = peluquero.descripcion
            tipos = peluquero.tipos
            servicios = peluquero.servicios
            ultimoLogin = peluquero.ultimoLogin
            vecesDeshabilitado = peluquero.vecesDeshabilitado
            fechaDeshabilitacion = peluquero.fechaDeshabilitacion
            id = peluquero.id
        }


    }
}