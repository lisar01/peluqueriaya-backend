package ar.edu.unq.peluqueriayabackend.service.emailSender.impl

import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.service.emailSender.EmailServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PeluqueriaYaEmailSender(@Autowired val emailServiceAPI: EmailServiceAPI) {

    fun enviarMailDeConfirmacion(turno: Turno){
        emailServiceAPI.sendEmail(turno.cliente.email,
                "El turno fue confirmado",
                "Hola el turno ha sido confirmado! Aproximadamente, en los proximos 30 minutos el peluquero " +
                        "llegará a la ubicacion especificada. Gracias por utilizar el servicio! Saludos :D!")
    }
}