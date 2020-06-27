package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "El turno ya fue calificado")
class TurnoYaCalificadoException : Exception("El turno ya fue calificado")