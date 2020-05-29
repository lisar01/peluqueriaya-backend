package ar.edu.unq.peluqueriayabackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/api/public")
    fun getPublic(): String {
        return "Ingreseeeee al publico"
    }

    @GetMapping("/api/private")
    fun getPrivate(): String {
        return "Ingreseeeee al privado"
    }

    @GetMapping("/api/private-cliente")
    fun getPrivateScopedCliente(): String {
        return "Ingreseeeee al privado scoped cliente"
    }

    @GetMapping("/api/private-peluquero")
    fun getPrivateScopedPeluquero(): String {
        return "Ingreseeeee al privado scoped peluquero"
    }

}