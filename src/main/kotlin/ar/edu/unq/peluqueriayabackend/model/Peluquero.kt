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
        var descripcion: String,
        @ElementCollection
        var tipos: MutableSet<PeluqueroType> = mutableSetOf(),
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "peluquero", orphanRemoval = true)
        var servicios:MutableList<Servicio> = mutableListOf(),
        @Id @GeneratedValue var id: Long? = null
                ) {

    fun agregarServicio(servicio: Servicio) {
        servicios.add(servicio)
    }

    fun isUnisex():Boolean = tipos.containsAll(setOf(PeluqueroType.HOMBRE,PeluqueroType.MUJER))

    data class Builder(
            var logo: String = "",
            var nombre: String = "",
            var corteMin: BigDecimal = BigDecimal(0),
            var distanciaMax: BigDecimal = BigDecimal(0),
            var email: String = "",
            var ubicacion:Ubicacion = Ubicacion("",""),
            var estado: PeluqueroState = PeluqueroState.DISPONIBLE,
            var descripcion: String = "",
            var tipos: MutableSet<PeluqueroType> = mutableSetOf(),
            var servicios:MutableList<Servicio> = mutableListOf()
    ){
        fun build():Peluquero {
            return Peluquero(logo,nombre,corteMin,distanciaMax,email,ubicacion,estado,descripcion,tipos,servicios)
        }

        fun withLogo(logo:String) = apply { this.logo = logo }
        fun withNombre(nombre:String) = apply { this.nombre = nombre }
        fun withCorteMin(corteMin: BigDecimal) = apply { this.corteMin = corteMin }
        fun withDistanciaMax(distanciaMax: BigDecimal) = apply { this.distanciaMax = distanciaMax}
        fun withEmail(email: String) = apply { this.email = email }
        fun withUbicacion(ubicacion: Ubicacion) = apply { this.ubicacion = ubicacion }
        fun withEstado(estado: PeluqueroState) = apply { this.estado = estado }
        fun withDescripcion(descripcion: String) = apply { this.descripcion = descripcion }
        fun withTipos(tipos: MutableSet<PeluqueroType>) = apply { this.tipos = tipos }
        fun withServicios(servicios: MutableList<Servicio>) = apply { this.servicios = servicios }

    }
}