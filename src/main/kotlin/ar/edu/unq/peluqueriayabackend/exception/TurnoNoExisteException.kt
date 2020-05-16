package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El turno no existe")
class TurnoNoExisteException(val id: Long) : Exception("El Turno con el id '$id' no existe")