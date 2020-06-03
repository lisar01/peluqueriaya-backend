package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.service.ClienteService
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/cliente")
@Validated
class ClienteController(val clienteService: ClienteService, val rolService: RolService) {

    @PostMapping
    fun getCliente(@Valid @RequestBody cliente: Cliente): Cliente {
        cliente.email = rolService.getEmail()
        return clienteService.save(cliente)
    }

}