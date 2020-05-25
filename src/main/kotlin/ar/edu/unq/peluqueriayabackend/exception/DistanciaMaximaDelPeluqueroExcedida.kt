package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "La distancia maxima que el peluquero permite fue excedida")
class DistanciaMaximaDelPeluqueroExcedida(): Exception("La distancia maxima que el peluquero permite fue excedida")