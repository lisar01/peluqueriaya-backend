package ar.edu.unq.peluqueriayabackend.controller

import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleGenericConstraintViolationException(e: ConstraintViolationException): Map<String, List<String>> {
        return  mapOf("message" to e.constraintViolations.map { it.message })
    }

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(e: BindException): Map<String, List<String?>> {
        return  mapOf("message" to e.fieldErrors.map { it.defaultMessage })
    }

}