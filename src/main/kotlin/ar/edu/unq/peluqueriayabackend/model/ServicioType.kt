package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ServicioType(val nombre: String) {
    CORTE("Corte"),
    ALISADO("Alisado"),
    UNAS("Uñas"),
    TENIDO("Teñido"),
    PEINADO("Peinado"),
    TRATAMIENTO("Tratamiento"),
    RECOGIDO("Recogido"),
    MECHAS("Mechas"),
    BARBERIA("Barberia"),
    OTROS("Otros");

    @JsonProperty("id")
    fun id() = this.name

}
