package ar.edu.unq.peluqueriayabackend.controller


import ar.edu.unq.peluqueriayabackend.controller.dtos.PerfilesDTO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RolController(val rolService: RolService, val peluqueroRepository: PeluqueroRepository) {

    @GetMapping("/perfil")
    fun getPerfiles(): PerfilesDTO? = rolService.getPerfiles(rolService.getEmail())

}