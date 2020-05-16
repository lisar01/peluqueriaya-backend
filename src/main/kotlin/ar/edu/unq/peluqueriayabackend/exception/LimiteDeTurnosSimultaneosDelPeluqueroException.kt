package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE,
        reason = "El peluquero ya posee el limite de turnos simultaneos")
class LimiteDeTurnosSimultaneosDelPeluqueroException(id:Long?, limite:Long):
        Exception("El peluquero con id $id ya posee el limite de turno max ($limite)")