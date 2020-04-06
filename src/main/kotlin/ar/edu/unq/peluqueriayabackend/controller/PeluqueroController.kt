package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/peluquero")
class PeluqueroController(@Autowired val peluqueroService: PeluqueroService) {
}