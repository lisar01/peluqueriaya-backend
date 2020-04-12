package ar.edu.unq.peluqueriayabackend.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "peluqueros")
class Peluquero(
        var logo: String,
        var nombre: String,
        var corteMin: BigDecimal,
        var distanciaMax: BigDecimal,
        var email: String,
        var ubicacion:Ubicacion,
        var estado: PeluqueroState,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "peluquero", orphanRemoval = true)
        var servicios:MutableList<Servicio> = mutableListOf()
                ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    fun agregarServicio(servicio: Servicio) {
        servicios.add(servicio)
    }

    data class Builder(
            var logo: String = "",
            var nombre: String = "",
            var corteMin: BigDecimal = BigDecimal(0),
            var distanciaMax: BigDecimal = BigDecimal(0),
            var email: String = "",
            var ubicacion:Ubicacion = Ubicacion("",""),
            var estado: PeluqueroState = PeluqueroState.DISPONIBLE,
            var servicios:MutableList<Servicio> = mutableListOf()
    ){
        fun build():Peluquero {
            return Peluquero(logo,nombre,corteMin,distanciaMax,email,ubicacion,estado,servicios)
        }

        fun logo(logo:String) = apply { this.logo = logo }
        fun withNombre(nombre:String) = apply { this.nombre = nombre }
        fun withCorteMin(corteMin: BigDecimal) = apply { this.corteMin = corteMin }
        fun withDistanciaMax(distanciaMax: BigDecimal) = apply { this.distanciaMax = distanciaMax}
        fun withEmail(email: String) = apply { this.email = email }
        fun withUbicacion(ubicacion: Ubicacion) = apply { this.ubicacion = ubicacion }
        fun withEstado(estado: PeluqueroState) = apply { this.estado = estado }
        fun withServicios(servicios: MutableList<Servicio>) = apply { this.servicios = servicios }

    }
}