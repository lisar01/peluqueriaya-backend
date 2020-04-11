package ar.edu.unq.peluqueriayabackend.model

import javax.persistence.Embeddable

@Embeddable
class Ubicacion(var latitude:String, var longitude:String){

    fun getLatitudeAsDouble():Double = latitude.toDouble()

    fun getLongitudeAsDouble():Double = longitude.toDouble()
}