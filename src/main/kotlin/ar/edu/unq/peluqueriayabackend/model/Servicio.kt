package ar.edu.unq.peluqueriayabackend.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "servicios")
class Servicio(var nombre:String,
               var precio:BigDecimal,
               @ManyToOne(fetch = FetchType.LAZY, optional = false)
               var peluquero:Peluquero) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0


    data class Builder(
            var nombre:String = "",
            var precio:BigDecimal = BigDecimal(0),
            var peluquero:Peluquero = Peluquero.Builder().build()
    ){
        fun build():Servicio {
            return Servicio(nombre,precio,peluquero)
        }
        fun withNombre(nombre: String) = apply { this.nombre }
        fun withPrecio(precio: BigDecimal) = apply { this.precio }
        fun withPeluquero(peluquero: Peluquero) = apply { this.peluquero }
    }

}