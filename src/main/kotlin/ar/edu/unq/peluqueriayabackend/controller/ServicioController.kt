package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.model.ServicioType
import ar.edu.unq.peluqueriayabackend.service.ServicioService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/servicio")
class ServicioController(val servicioService: ServicioService) {

    @GetMapping("/tipos")
    fun obtenerTodosLosTiposDeServicio(): Array<ServicioType> {
        return ServicioType.values();
    }
}