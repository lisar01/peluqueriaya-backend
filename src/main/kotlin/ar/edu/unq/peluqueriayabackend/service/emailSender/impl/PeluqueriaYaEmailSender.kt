package ar.edu.unq.peluqueriayabackend.service.emailSender.impl

import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.service.emailSender.EmailServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PeluqueriaYaEmailSender(@Autowired val emailServiceAPI: EmailServiceAPI) {

    fun enviarMailDeConfirmacion(turno: Turno){
        emailServiceAPI.sendEmail(turno.cliente.emailOpcional,
                "El turno fue confirmado",
                "Hola el turno ha sido confirmado! Aproximadamente, en los proximos 30 minutos el peluquero " +
                        "llegará a la ubicacion especificada. Gracias por utilizar el servicio! Saludos :D!")
    }

    fun enviarMailAlClienteQueTerminoSuEspera(turno: Turno) {
        emailServiceAPI.sendEmail(turno.cliente.emailOpcional,
        "El turno ahora esta en Pendiente",
        "Hola el turno está en pendiente, eso quiere decir que esta cada vez mas cerca de ser atendido!")
    }

    fun enviarMailAlClienteQueSeCanceloElTurno(turno: Turno) {
        emailServiceAPI.sendEmail(turno.cliente.emailOpcional,
        "El turno fue cancelado",
        "El turno fue cancelado lamentablemente :C. " +
                "Puede pedir otro turno a otro peluquero o volver a intentar con el mismo si este está conectado!")
    }

    fun enviarMailAlClienteQueSuTurnoEstaEnEspera(turno:Turno) {
        emailServiceAPI.sendEmail(turno.cliente.emailOpcional,
        "El turno esta en la cola de espera del peluquero",
        "Hola, su turno fue ingresado en la cola de espera del peluquero porque este posee completa su casilla de turnos. "+
        "Es muy probable que su turno demore mucho, si quiere puede cancelar el turno en su perfil de turnos. Saludos! :D")
    }

    fun enviarMailDeFinalizacionTurno(turno: Turno) {
        emailServiceAPI.sendEmail(turno.cliente.emailOpcional,
            "El turno ha finalizado, ya puede calificar el servicio",
        "Hola, el turno ha sido marcado como finalizado por el peluquero. " +
                "Puede calificar su turno en nuestra pagina en la sección \"Mis turnos\">\"Turnos Historicos\" "+
                "para ofrecer un feedback del servicio contratado. " +
                "En caso de haber existido algun tipo de problema, contactenos al email peluqueria-ya-soporte@gmail.com. "+
                "Saludos y muchas gracias por usar nuestro servicio! :D")
    }
}