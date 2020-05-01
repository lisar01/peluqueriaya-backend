package ar.edu.unq.peluqueriayabackend.model

import javax.persistence.Embeddable

@Embeddable
class Ubicacion(var latitude:String, var longitude:String) {

    @Suppress("JpaAttributeMemberSignatureInspection")
    fun getLatitudeAsDouble():Double = latitude.toDouble()

    @Suppress("JpaAttributeMemberSignatureInspection")
    fun getLongitudeAsDouble():Double = longitude.toDouble()

}