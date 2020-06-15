package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.ServicioDTO
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import ar.edu.unq.peluqueriayabackend.service.RolService
import ar.edu.unq.peluqueriayabackend.service.ServicioService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/servicio")
@Validated
class ServicioController(val servicioService: ServicioService, val rolService: RolService) {

    @GetMapping("/tipos")
    fun obtenerTodosLosTiposDeServicio(): Array<ServicioType> = ServicioType.values()

    @PostMapping
    @ResponseStatus(HttpStatus.OK, reason = "Servicio creado exitosamente!")
    fun guardar(@Valid @RequestBody servicioDTO: ServicioDTO) {
        servicioService.guardar(servicioDTO, rolService.getEmail())
    }

}