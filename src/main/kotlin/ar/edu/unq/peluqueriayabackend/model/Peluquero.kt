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
        private var estado: PeluqueroState,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "peluquero", orphanRemoval = true)
        var servicios:List<Servicio>
                ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

}