package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "El Peluquero esta desconectado")
class PeluqueroDesconectado() : Exception("El peluquero esta desconetado/deshabilitado") {
}