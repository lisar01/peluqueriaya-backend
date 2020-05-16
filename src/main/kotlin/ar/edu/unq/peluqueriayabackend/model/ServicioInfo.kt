package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import javax.persistence.*

// Creo esta clase para que quede una foto de los precios de los servicios contratados

@Entity
@Table(name = "servicios_info")
class ServicioInfo(
        var nombre:String,
        var precio:BigDecimal,
        @JsonIgnore
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        var turno: Turno,
        @Id @GeneratedValue var id: Long? = null
){

        data class Builder(
                var nombre: String = "",
                var precio: BigDecimal = BigDecimal.ZERO,
                var turno: Turno = Turno.Builder().build()
        ){
                fun build():ServicioInfo {
                        return ServicioInfo(nombre,precio,turno)
                }

                fun withNombre(nombre: String) = apply { this.nombre = nombre }
                fun withPrecio(precio:BigDecimal) = apply { this.precio = precio }
                fun withTurno(turno: Turno) = apply { this.turno = turno }

        }
}