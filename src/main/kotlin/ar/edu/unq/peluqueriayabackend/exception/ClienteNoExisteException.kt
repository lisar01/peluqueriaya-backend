package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "El cliente no existe")
class ClienteNoExisteException(val id:Long) : Exception("El cliente con id '$id' no existe")