package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Ya se encuentra registrado como peluquero")
class PeluqueroYaExisteException : RuntimeException("Ya se encuentra registrado como peluquero")
