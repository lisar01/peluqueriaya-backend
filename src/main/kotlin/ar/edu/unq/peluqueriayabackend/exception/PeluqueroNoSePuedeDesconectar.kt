package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "El Peluquero no puede desconectarse")
class PeluqueroNoSePuedeDesconectar() : Exception("El peluquero no se puede desconectar")