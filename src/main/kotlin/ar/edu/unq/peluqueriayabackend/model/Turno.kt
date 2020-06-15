package ar.edu.unq.peluqueriayabackend.model

import ar.edu.unq.peluqueriayabackend.exception.ValorDePuntuacionErroneo
import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "turnos")
class Turno (
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "peluquero_id")
        @JsonIgnore
        var peluquero: Peluquero,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "cliente_id")
        @JsonIgnore
        var cliente: Cliente,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY,
                mappedBy = "turno", orphanRemoval = true)
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
        @Id @GeneratedValue var id: Long? = null
) {

    fun precioTotal() : BigDecimal {
        return if(serviciosSolicitadosInfo.isEmpty()){
            corteMinInfo
        }else{
            serviciosSolicitadosInfo.fold(corteMinInfo) { r, sv -> r + sv.precio }
        }
    }

    fun getPeluqueroId():Long? = peluquero.id

    fun getClienteId():Long? = cliente.id

    fun puntuar(valor: Int) {
        if(valor !in 1..5)
            throw ValorDePuntuacionErroneo()
        estado.puntuar(valor,this)
    }

    fun confirmar() {
        estado.confirmar(this)
    }

    fun finalizar() {
        estado.finalizar(this)
    }

    fun cancelar() {
        estado.cancelar(this)
    }

    fun terminarEsperaTurno() {
        estado.terminarEsperaTurno(this)
    }

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

    data class Builder(
            var peluquero: Peluquero = Peluquero.Builder().withCorteMin(BigDecimal.ZERO).build(),
            var cliente: Cliente = Cliente.Builder().build(),
            var serviciosSolicitados: MutableList<ServicioInfo> = mutableListOf(),
            var fechaInicio: LocalDateTime = LocalDateTime.now(),
            var fechaCancelacion: LocalDateTime? = null,
            var fechaPendiente: LocalDateTime? = null,
            var fechaConfirmacion: LocalDateTime? = null,
            var fechaFin: LocalDateTime? = null,
            var estado: TurnoState = TurnoState.ESPERANDO,
            var puntaje: Int = 0,
            var corteMinInfo: BigDecimal = BigDecimal.ZERO,
            var ubicacionDelTurno: Ubicacion = Ubicacion("","")
    ){
        fun build():Turno {
            return Turno(peluquero,cliente,serviciosSolicitados,
                    fechaInicio,fechaCancelacion, fechaPendiente, fechaConfirmacion, fechaFin,estado,
                    puntaje, corteMinInfo, ubicacionDelTurno)
        }

        fun withPeluquero(peluquero: Peluquero) = apply { this.peluquero = peluquero }
        fun withCliente(cliente:Cliente) = apply { this.cliente = cliente }
        fun withServiciosSolicitadosInfo(servicios:MutableList<ServicioInfo>) = apply { this.serviciosSolicitados = servicios }
        fun withFechaInicio(fechaInicio: LocalDateTime) = apply { this.fechaInicio = fechaInicio }
        fun withFechaCancelacion(fechaCancelacion: LocalDateTime) = apply { this.fechaCancelacion = fechaCancelacion}
        fun withFechaPendiente(fechaPendiente: LocalDateTime) = apply { this.fechaPendiente = fechaPendiente }
        fun withFechaConfirmacion(fechaConfirmacion: LocalDateTime) = apply { this.fechaConfirmacion = fechaConfirmacion }
        fun withFechaFin(fechaFin: LocalDateTime) = apply { this.fechaFin = fechaFin }
        fun withEstado(estado:TurnoState) = apply { this.estado = estado }
        fun withPuntaje(puntaje: Int) = apply { this.puntaje = puntaje }
        fun withCorteMinInfo(corteMinInfo: BigDecimal) = apply { this.corteMinInfo = corteMinInfo }
        fun withUbicacionDelTurno(ubicacionDelTurno: Ubicacion) = apply { this.ubicacionDelTurno = ubicacionDelTurno }

    }
}