package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.Length
import javax.persistence.Embeddable
import javax.validation.constraints.Pattern

@Embeddable
class Ubicacion(
        @field:Length(min = 10, message="{direccion.minimo}")
        var direccion:String,
        @field:Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message = "{latitud.invalida}")
        var latitude:String,
        @field:Pattern(regexp = "-?[1-9][0-9]*(\\.[0-9]+)?", message="{longitud.invalida}")
        var longitude:String) {

    @Suppress("JpaAttributeMemberSignatureInspection")
    @JsonIgnore
    fun getLatitudeAsDouble():Double = latitude.toDouble()

    @Suppress("JpaAttributeMemberSignatureInspection")
    @JsonIgnore
    fun getLongitudeAsDouble():Double = longitude.toDouble()

}