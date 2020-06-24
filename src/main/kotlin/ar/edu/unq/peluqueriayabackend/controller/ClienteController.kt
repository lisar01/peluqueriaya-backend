package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.controller.dtos.ClienteEditarDatosDTO
import ar.edu.unq.peluqueriayabackend.exception.ClienteNoExisteException
import ar.edu.unq.peluqueriayabackend.exception.ClienteYaExisteException
import ar.edu.unq.peluqueriayabackend.model.Cliente
import ar.edu.unq.peluqueriayabackend.service.ClienteService
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
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

    @GetMapping
    fun getClienteLogged() : Cliente = getMaybeClienteByJWT().get()

    @PostMapping("/editar")
    @ResponseStatus(HttpStatus.OK, reason = "Los datos han sido editados exitosamente!")
    fun editarDatos(@Valid @RequestBody clienteEditarDatosDTO: ClienteEditarDatosDTO){
        val maybeCliente = getMaybeClienteByJWT()
        if(!maybeCliente.isPresent)
            throw ClienteNoExisteException()

        clienteService.update(clienteEditarDatosDTO.editarDatosCliente(maybeCliente.get()))
    }

    private fun getMaybeClienteByJWT(): Optional<Cliente> {
        val emailCliente = rolService.getEmail()
        return clienteService.getByEmail(emailCliente)
    }


}