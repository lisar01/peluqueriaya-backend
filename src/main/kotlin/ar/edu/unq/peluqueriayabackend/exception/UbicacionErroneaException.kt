package ar.edu.unq.peluqueriayabackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Ubicacion erronea")
class UbicacionErroneaExcepcion: RuntimeException() {
}