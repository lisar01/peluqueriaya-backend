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
        var servicios:List<Servicio>
                ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    data class Builder(
            var logo: String = "",
            var nombre: String = "",
            var corteMin: BigDecimal = BigDecimal(0),
            var distanciaMax: BigDecimal = BigDecimal(0),
            var email: String = "",
            var ubicacion:Ubicacion = Ubicacion("",""),
            var estado: PeluqueroState = PeluqueroState.DISPONIBLE,
            var servicios:List<Servicio> = emptyList()
    ){
        fun build():Peluquero {
            return Peluquero(logo,nombre,corteMin,distanciaMax,email,ubicacion,estado,servicios)
        }

        fun withLogo(logo:String) = apply { this.logo }
        fun withNombre(nombre:String) = apply { this.nombre }
        fun withCorteMin(corteMin: BigDecimal) = apply { this.corteMin }
        fun withDistanciaMax(distanciaMax: BigDecimal) = apply { this.distanciaMax }
        fun withEmail(email: String) = apply { this.email }
        fun withUbicacion(ubicacion: Ubicacion) = apply { this.ubicacion }
        fun withEstado(estado: PeluqueroState) = apply { this.estado }
        fun withServicios(servicios: List<Servicio>) = apply { this.servicios }

    }
}