package ar.edu.unq.peluqueriayabackend.model

import javax.persistence.Embeddable

@Embeddable
data class Ubicacion(var altitude:String, var longitude:String){
}