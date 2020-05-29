package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ClienteRepository
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.service.RolService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service

@Service
class RolServiceImpl(val clienteRepository: ClienteRepository, val peluqueroRepository: PeluqueroRepository)
    : RolService {

    val NAMESPACE_URI: String = "https://peluqueria-ya.com/email"

    override fun getEmail(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        return (authentication as JwtAuthenticationToken).tokenAttributes.get(NAMESPACE_URI) as String
    }

    override fun tieneRolCliente(): Boolean {
        return clienteRepository.existsByEmail(getEmail())
    }

    override fun tieneRolPeluquero(): Boolean {
        return peluqueroRepository.existsByEmail(getEmail())
    }

}