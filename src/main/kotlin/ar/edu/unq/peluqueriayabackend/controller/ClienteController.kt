package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.ClienteEditarDatosDTO
import ar.edu.unq.peluqueriayabackend.exception.ClienteYaExisteException
import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.service.ClienteService
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/cliente")
@Validated
class ClienteController(val clienteService: ClienteService, val rolService: RolService) {

    @PostMapping
    @ResponseStatus(HttpStatus.OK, reason = "Cuenta creada exitosamente!")
    fun guardar(@Valid @RequestBody cliente: Cliente) {
        cliente.email = rolService.getEmail()
        if (rolService.tieneRolCliente()) {
            throw ClienteYaExisteException()
        }
        clienteService.save(cliente)
    }

    @PutMapping
    fun editar(@Valid @RequestBody clienteEditarDatosDTO: ClienteEditarDatosDTO): Cliente {
        val clienteAModificar = clienteService.getByEmail(rolService.getEmail()).get()
        return clienteService.update(clienteEditarDatosDTO.editarDatosCliente(clienteAModificar))
    }

}