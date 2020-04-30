package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "servicios")
class Servicio(var nombre:String,
               var precio:BigDecimal,
               @JsonIgnore
               @ManyToOne(fetch = FetchType.LAZY, optional = false)
               var peluquero:Peluquero,
               @Id @GeneratedValue var id: Long? = null) {

    data class Builder(
            var nombre:String = "",
            var precio:BigDecimal = BigDecimal(0),
            var peluquero:Peluquero = Peluquero.Builder().build()
    ){
        fun build():Servicio {
            return Servicio(nombre,precio,peluquero)
        }
        fun withNombre(nombre: String) = apply { this.nombre = nombre }
        fun withPrecio(precio: BigDecimal) = apply { this.precio = precio}
        fun withPeluquero(peluquero: Peluquero) = apply { this.peluquero = peluquero}
    }

}