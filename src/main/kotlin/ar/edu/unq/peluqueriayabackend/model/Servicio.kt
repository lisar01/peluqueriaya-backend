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

}