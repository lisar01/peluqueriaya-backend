package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "servicios")
class Servicio(var descripcion: String,
               @ElementCollection
               var tipos: MutableSet<ServicioType>,
               var precio: BigDecimal,
               @JsonIgnore
               @ManyToOne(fetch = FetchType.LAZY, optional = false)
               var peluquero: Peluquero,
               @Id @GeneratedValue var id: Long? = null) {

    data class Builder(
            var descripcion: String = "",
            var tipos: MutableSet<ServicioType> = mutableSetOf(),
            var precio: BigDecimal = BigDecimal(0),
            var peluquero: Peluquero = Peluquero.Builder().build()
    ) {
        fun build(): Servicio {
            return Servicio(descripcion, tipos, precio, peluquero)
        }

        fun withDescripcion(descripcion: String) = apply { this.descripcion = descripcion }
        fun withTipos(tipos: MutableSet<ServicioType>) = apply { this.tipos = tipos }
        fun withPrecio(precio: BigDecimal) = apply { this.precio = precio }
        fun withPeluquero(peluquero: Peluquero) = apply { this.peluquero = peluquero }
    }

}