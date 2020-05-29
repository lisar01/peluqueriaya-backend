package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.web.bind.annotation.RestController

@RestController
class RolController(val rolService: RolService) {

}