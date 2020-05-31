package ar.edu.unq.peluqueriayabackend.controller


import ar.edu.unq.peluqueriayabackend.controller.dtos.RolesDTO
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RolController(val rolService: RolService) {

    @GetMapping("/roles")
    fun getRolesDeUsuario(): RolesDTO {
        return rolService.getRolesByEmail(rolService.getEmail())
    }

}