package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.NaturalId
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Suppress("JpaDataSourceORMInspection")
@Entity
@Table(name = "peluqueros")
class Peluquero(
        var logo: String,
        var nombre: String,
        var corteMin: BigDecimal,
        var distanciaMax: BigDecimal = BigDecimal(0),
        @NaturalId
        @field:JsonIgnore
        var email: String,
        var emailOpcional:String,
        var ubicacion:Ubicacion,
        var estado: PeluqueroState,
        var descripcion: String,
        @ElementCollection
        var tipos: MutableSet<PeluqueroType> = mutableSetOf(),
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "peluquero", orphanRemoval = true)
        var servicios:MutableList<Servicio> = mutableListOf(),
        var ultimoLogin: LocalDateTime = LocalDateTime.now(),
        var vecesDeshabilitado: Int = 0,
        var fechaDeshabilitacion: LocalDateTime?,
        @Id @GeneratedValue var id: Long? = null
                ) {

    fun desconectar() {
        estado.desconectar(this)
    }

    fun conectar() {
        estado.conectar(this)
    }

    fun ocupar() {
        estado.ocupar(this)
    }

    fun desocupar() {
        estado.desocupar(this)
    }

    fun deshabilitar() {
        estado.deshabilitar(this)
    }

    fun habilitar() {
        estado.habilitar(this)
    }

    fun noPoseeMasTurnos() {
        estado.noPoseeMasTurnos(this)
    }

    fun poseeTurnos() {
        estado.poseeTurnos(this)
    }

    fun getTieneTurnosEnEspera() : Boolean = estado.tieneTurnosEnEspera()

    fun getEstaDisponible() : Boolean = estado.estaDisponible()

    fun getEstaOcupado() : Boolean = estado.estaOcupado()

    fun getEstaDesconectado() : Boolean = estado.estaDesconectado()

    fun getEstaDeshabilitado() : Boolean = estado.estaDeshabilitado()

    fun agregarServicio(servicio : Servicio) {
        servicios.add(servicio)
    }

    fun eliminarServicio(id: Long): Boolean = servicios.removeIf {  it.id == id  }

    fun agregarTipo(tipo:PeluqueroType) {
        tipos.add(tipo)
    }

    fun eliminarTipo(tipo:PeluqueroType) {
        tipos.remove(tipo)
    }

    data class Builder(
            var logo: String = "",
            var nombre: String = "",
            var corteMin: BigDecimal = BigDecimal(0),
            var distanciaMax: BigDecimal = BigDecimal(0),
            var email: String = "",
            var emailOpcional:String= "",
            var ubicacion:Ubicacion = Ubicacion("","0","0"),
            var estado: PeluqueroState = PeluqueroState.DISPONIBLE,
            var descripcion: String = "",
            var tipos: MutableSet<PeluqueroType> = mutableSetOf(),
            var servicios:MutableList<Servicio> = mutableListOf(),
            var ultimoLogin: LocalDateTime = LocalDateTime.now(),
            var vecesDeshabilitado: Int = 0,
            var fechaDeshabilitacion: LocalDateTime? = null
    ){
        fun build():Peluquero {
            return Peluquero(logo,nombre,corteMin,distanciaMax,email,emailOpcional,ubicacion,estado,descripcion,tipos,servicios,ultimoLogin, vecesDeshabilitado, fechaDeshabilitacion)
        }

        fun withLogo(logo:String) = apply { this.logo = logo }
        fun withNombre(nombre:String) = apply { this.nombre = nombre }
        fun withCorteMin(corteMin: BigDecimal) = apply { this.corteMin = corteMin }
        fun withDistanciaMax(distanciaMax: BigDecimal) = apply { this.distanciaMax = distanciaMax}
        fun withEmail(email: String) = apply {
            this.email = email
            this.emailOpcional = email
        }
        fun withEmailOpcional(emailOpcional: String) = apply { this.emailOpcional = emailOpcional }
        fun withUbicacion(ubicacion: Ubicacion) = apply { this.ubicacion = ubicacion }
        fun withEstado(estado: PeluqueroState) = apply { this.estado = estado }
        fun withDescripcion(descripcion: String) = apply { this.descripcion = descripcion }
        fun withTipos(tipos: MutableSet<PeluqueroType>) = apply { this.tipos = tipos }
        fun withServicios(servicios: MutableList<Servicio>) = apply { this.servicios = servicios }
        fun withUltimoLogin(ultimoLogin: LocalDateTime) = apply { this.ultimoLogin = ultimoLogin }
        fun withVecesDeshabilitado(vecesDeshabilitado: Int) = apply { this.vecesDeshabilitado = vecesDeshabilitado }
        fun withFechaDeshabilitacion(fechaDeshabilitacion: LocalDateTime) = apply { this.fechaDeshabilitacion = fechaDeshabilitacion }

    }
}