package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.controller.dtos.RolType
import ar.edu.unq.peluqueriayabackend.controller.dtos.RolesDTO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ClienteRepository
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class RolServiceImpl(val clienteRepository: ClienteRepository, val peluqueroRepository: PeluqueroRepository)
    : RolService {

    val NAMESPACE_URI: String = "https://peluqueria-ya.com/email"

    override fun getEmail(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return (authentication as JwtAuthenticationToken).tokenAttributes[NAMESPACE_URI] as String
    }

    @Transactional
    override fun tieneRolCliente(): Boolean = clienteRepository.existsByEmail(getEmail())

    @Transactional
    override fun tieneRolPeluquero(): Boolean = peluqueroRepository.existsByEmail(getEmail())

    override fun getRolesByEmail(email: String): RolesDTO = RolesDTO(tieneRolCliente(), tieneRolPeluquero())

}