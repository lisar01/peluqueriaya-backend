package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.exception.APIError
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e: ConstraintViolationException): APIError {
        val subErrors : List<String> = e.constraintViolations.map { it.message }
        return APIError("Los datos no son validos", HttpStatus.BAD_REQUEST, "", subErrors)
    }

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(e: BindException): APIError {
        val subErrors = e.fieldErrors.map { it.defaultMessage }
        return APIError("${e.objectName} no es valido/a", HttpStatus.BAD_REQUEST, e.nestedPath, subErrors)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNoHandlerFoundException(response: HttpServletResponse?, ex: NoHandlerFoundException): APIError {
        return APIError("La pagina solicitada no existe", HttpStatus.NOT_FOUND, ex.requestURL)
    }

}