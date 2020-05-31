package ar.edu.unq.peluqueriayabackend.model

import org.hibernate.annotations.NaturalId
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Suppress("JpaDataSourceORMInspection")
@Entity
@Table(name = "clientes")
class Cliente(
        var imgPerfil: String,
        var nombre:String,
        var apellido:String,
        @NaturalId
        var email:String,
        var emailOpcional:String,
        var nroTelefono:String,
        var ubicacion: Ubicacion,
        var estado: ClienteState = ClienteState.ACTIVO,
        @Id @GeneratedValue var id: Long? = null
) {

    fun getFullName() : String = "$nombre $apellido"

    data class Builder(
            var imgPerfil: String = "",
            var nombre:String = "",
            var apellido:String = "",
            var email:String = "",
            var emailOpcional:String= "",
            var nroTelefono:String = "",
            var ubicacion: Ubicacion = Ubicacion("0","0"),
            var estado: ClienteState = ClienteState.ACTIVO
    ){
        fun build():Cliente {
            return Cliente(imgPerfil,nombre,apellido,email,emailOpcional,nroTelefono, ubicacion, estado)
        }

        fun withImgPerfil(imgPerfil: String) = apply { this.imgPerfil = imgPerfil }
        fun withNombre(nombre:String) = apply { this.nombre = nombre }
        fun withApellido(apellido: String) = apply { this.apellido = apellido }
        fun withEmail(email: String) = apply {
            this.email = email
            this.emailOpcional = email
        }
        fun withEmailOpcional(emailOpcional: String) = apply { this.emailOpcional = emailOpcional }
        fun withNroTelefono(nroTelefono: String) = apply { this.nroTelefono = nroTelefono }
        fun withUbicacion(ubicacion: Ubicacion) = apply { this.ubicacion = ubicacion }
        fun withEstado(estado: ClienteState) = apply { this.estado = estado }

    }

}