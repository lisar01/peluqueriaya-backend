package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "El turno no se puede calificar")
class TurnoNoSePuedeCalificar : Exception("El turno no se puede calificar")