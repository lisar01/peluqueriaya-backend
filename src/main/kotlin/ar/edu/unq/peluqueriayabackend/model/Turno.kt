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
        var fechaConfirmacion: LocalDateTime?,
        var fechaFin: LocalDateTime?,
        var estado: TurnoState,
        var puntaje: Int = 0,
        var corteMinInfo: BigDecimal,
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

    fun getUbicacionCliente():Ubicacion = cliente.ubicacion

    fun puntuar(valor: Int) {
        if(valor !in 1..5)
            throw ValorDePuntuacionErroneo()
        estado.puntuar(valor,this)
    }

    fun confirmarTurno() {
        estado.confirmarTurno(this)
    }

    fun finalizarTurno() {
        estado.finalizarTurno(this)
    }

    data class Builder(
            var peluquero: Peluquero = Peluquero.Builder().withCorteMin(BigDecimal.ZERO).build(),
            var cliente: Cliente = Cliente.Builder().build(),
            var serviciosSolicitados: MutableList<ServicioInfo> = mutableListOf(),
            var fechaInicio: LocalDateTime = LocalDateTime.now(),
            var fechaConfirmacion: LocalDateTime? = null,
            var fechaFin: LocalDateTime? = null,
            var estado: TurnoState = TurnoState.PENDIENTE,
            var puntaje: Int = 0,
            var corteMinInfo: BigDecimal = BigDecimal.ZERO
    ){
        fun build():Turno {
            return Turno(peluquero,cliente,serviciosSolicitados,
                    fechaInicio,fechaConfirmacion, fechaFin,estado,
                    puntaje, corteMinInfo)
        }

        fun withPeluquero(peluquero: Peluquero) = apply { this.peluquero = peluquero }
        fun withCliente(cliente:Cliente) = apply { this.cliente = cliente }
        fun withServiciosSolicitadosInfo(servicios:MutableList<ServicioInfo>) = apply { this.serviciosSolicitados = servicios }
        fun withFechaInicio(fechaInicio: LocalDateTime) = apply { this.fechaInicio = fechaInicio }
        fun withFechaConfirmacion(fechaConfirmacion: LocalDateTime) = apply { this.fechaConfirmacion = fechaConfirmacion }
        fun withFechaFin(fechaFin: LocalDateTime) = apply { this.fechaFin = fechaFin }
        fun withEstado(estado:TurnoState) = apply { this.estado = estado }
        fun withPuntaje(puntaje: Int) = apply { this.puntaje = puntaje }
        fun withCorteMinInfo(corteMinInfo: BigDecimal) = apply { this.corteMinInfo = corteMinInfo }

    }
}