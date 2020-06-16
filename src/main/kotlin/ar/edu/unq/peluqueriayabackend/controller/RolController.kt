package ar.edu.unq.peluqueriayabackend.controller


import ar.edu.unq.peluqueriayabackend.controller.dtos.PerfilesDTO
import ar.edu.unq.peluqueriayabackend.controller.dtos.RolesDTO
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ServicioRepository
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RolController(val rolService: RolService) {

    @GetMapping("/roles")
    fun getRolesDeUsuario(): RolesDTO = rolService.getRolesByEmail(rolService.getEmail())

    @GetMapping("/perfil")
    fun getPerfiles(): PerfilesDTO? = rolService.getPerfiles(rolService.getEmail())

}