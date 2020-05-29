package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.RolType
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RolController(val rolService: RolService) {

    @GetMapping("/roles")
    fun getRolesDeUsuario(): RolType {
        return rolService.getRolesByEmail(rolService.getEmail())
    }

}