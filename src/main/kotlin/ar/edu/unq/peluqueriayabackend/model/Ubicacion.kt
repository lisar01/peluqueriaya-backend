package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Embeddable
import javax.validation.constraints.Pattern

@Embeddable
class Ubicacion(
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