package ar.edu.unq.peluqueriayabackend.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "clientes")
class Cliente(
        var imgPerfil: String,
        var nombre:String,
        var apellido:String,
        var email:String,
        var nroTelefono:String,
        var ubicacion: Ubicacion?,
        @Id @GeneratedValue var id: Long? = null
) {

    fun getFullName() : String = "$nombre $apellido"

    data class Builder(
            var imgPerfil: String = "",
            var nombre:String = "",
            var apellido:String = "",
            var email:String = "",
            var nroTelefono:String = "",
            var ubicacion: Ubicacion? = null
    ){
        fun build():Cliente {
            return Cliente(imgPerfil,nombre,apellido,email,nroTelefono, ubicacion)
        }

        fun withImgPerfil(imgPerfil: String) = apply { this.imgPerfil = imgPerfil }
        fun withNombre(nombre:String) = apply { this.nombre = nombre }
        fun withApellido(apellido: String) = apply { this.apellido = apellido }
        fun withEmail(email: String) = apply { this.email = email }
        fun withNroTelefono(nroTelefono: String) = apply { this.nroTelefono = nroTelefono }
        fun withUbicacion(ubicacion: Ubicacion) = apply { this.ubicacion = ubicacion }

    }

}