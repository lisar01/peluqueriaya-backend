package ar.edu.unq.peluqueriayabackend.model

import java.lang.Exception
import javax.persistence.Embeddable

@Embeddable
class Ubicacion(var latitude:String, var longitude:String){

    fun getLatitudeAsDouble():Double = latitude.toDouble()

    fun getLongitudeAsDouble():Double = longitude.toDouble()

    fun isValid():Boolean{
        try{
            getLatitudeAsDouble()
            getLongitudeAsDouble()
        }catch (e:Exception){
            return false
        }
        return true
    }
}