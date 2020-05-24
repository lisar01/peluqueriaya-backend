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

    fun enviarMailAlClienteQueTerminoSuEspera(turno: Turno) {
        emailServiceAPI.sendEmail(turno.cliente.email,
        "El turno ahora esta en Pendiente",
        "Hola el turno está en pendiente, eso quiere decir que esta cada vez mas cerca de ser atendido!")
    }

    fun enviarMailAlClienteQueSeCanceloElTurno(turno: Turno) {
        emailServiceAPI.sendEmail(turno.cliente.email,
        "El turno fue cancelado",
        "El turno fue cancelado lamentablemente :C. " +
                "Puede pedir otro turno a otro peluquero o volver a intentar con el mismo si este está conectado!")
    }
}