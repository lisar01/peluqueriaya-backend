package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.*
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.time.LocalDateTime

class TurnoConDireccionDTO(
        @JsonIgnore
        var peluquero: Peluquero,
        @JsonIgnore
        var cliente: Cliente,
        var serviciosSolicitadosInfo: MutableList<ServicioInfo>,
        var fechaInicio: LocalDateTime = LocalDateTime.now(),
        var fechaCancelacion: LocalDateTime?,
        var fechaPendiente: LocalDateTime?,
        var fechaConfirmacion: LocalDateTime?,
        var fechaFin: LocalDateTime?,
        var estado: TurnoState = TurnoState.ESPERANDO,
        var puntaje: Int = 0,
        var corteMinInfo: BigDecimal,
        var ubicacionDelTurno: Ubicacion,
        var direccionDelTurno:String,
        var id: Long? = null
)
{

    fun getPeluqueroId():Long? = peluquero.id

    fun getClienteId():Long? = cliente.id

    fun getEstaPendiente():Boolean = estado.estaPendiente()

    fun getEstaConfirmado():Boolean = estado.estaConfirmado()

    fun getEstaFinalizado():Boolean = estado.estaFinalizado()

    fun getEstaCancelado():Boolean = estado.estaCancelado()

    fun getEstaEsperando():Boolean = estado.estaEsperando()

    fun getClienteEmail():String = cliente.emailOpcional

    fun getClienteFullName():String = cliente.getFullName()

    fun getPeluqueroName():String = peluquero.nombre

    fun getPeluqueroEmailOpcional():String = peluquero.emailOpcional

    fun getPeluqueroLogo():String = peluquero.logo

    fun getClienteNroTelefono():String = cliente.nroTelefono

    fun getClienteImgPerfil():String = cliente.imgPerfil

    class Builder(
            var peluquero: Peluquero = Peluquero.Builder().build(),
            var cliente: Cliente = Cliente.Builder().build(),
            var serviciosSolicitadosInfo: MutableList<ServicioInfo> = mutableListOf(),
            var fechaInicio: LocalDateTime = LocalDateTime.now(),
            var fechaCancelacion: LocalDateTime? = null,
            var fechaPendiente: LocalDateTime? = null,
            var fechaConfirmacion: LocalDateTime? = null,
            var fechaFin: LocalDateTime? = null,
            var estado: TurnoState = TurnoState.ESPERANDO,
            var puntaje: Int = 0,
            var corteMinInfo: BigDecimal = BigDecimal.ZERO,
            var ubicacionDelTurno: Ubicacion = Ubicacion("","",""),
            var direccionDelTurno:String = "",
            var id: Long? = null
    ){
        fun build() : TurnoConDireccionDTO{
            return TurnoConDireccionDTO(peluquero, cliente,serviciosSolicitadosInfo, fechaInicio,
                fechaCancelacion, fechaPendiente, fechaConfirmacion, fechaFin, estado, puntaje,
                    corteMinInfo, ubicacionDelTurno, direccionDelTurno, id)
        }

        fun withTurno(turno: Turno) = apply {
            peluquero = turno.peluquero
            cliente = turno.cliente
            serviciosSolicitadosInfo = turno.serviciosSolicitadosInfo
            fechaInicio = turno.fechaInicio
            fechaCancelacion = turno.fechaCancelacion
            fechaPendiente = turno.fechaPendiente
            fechaConfirmacion = turno.fechaConfirmacion
            fechaFin = turno.fechaFin
            estado = turno.estado
            puntaje = turno.puntaje
            corteMinInfo = turno.corteMinInfo
            ubicacionDelTurno = turno.ubicacionDelTurno
            direccionDelTurno = turno.ubicacionDelTurno.direccion
            id = turno.id
        }

    }
}