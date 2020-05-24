package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "El Peluquero no posee mas turnos en espera")
class PeluqueroNoPoseeMasTurnos() : Exception("El peluquero no posee mas turnos en espera")