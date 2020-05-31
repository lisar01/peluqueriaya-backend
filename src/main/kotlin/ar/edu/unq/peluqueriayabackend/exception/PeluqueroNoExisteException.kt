package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El peluquero no existe")
class PeluqueroNoExisteException() : Exception("El peluquero no existe")