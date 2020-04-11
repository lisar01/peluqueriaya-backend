package ar.edu.unq.peluqueriayabackend.model

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "peluqueros")
class Peluquero(
        var logo: String,
        var nombre: String,
        var corteMin: BigDecimal,
        var distanciaMax: Long?,
        var email: String,
        var ubicacion:Ubicacion,
        var estado: PeluqueroState = PeluqueroState.DISPONIBLE,
        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "peluquero", orphanRemoval = true)
        var servicios:List<Servicio>
                ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

}