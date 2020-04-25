package ar.edu.unq.peluqueriayabackend.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST, reason = "Campos invalidos")
    fun handleGenericNotFoundException(e: ConstraintViolationException): List<String> {
        return e.constraintViolations.map { it.message }
    }

}