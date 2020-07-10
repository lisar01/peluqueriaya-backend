package ar.edu.unq.peluqueriayabackend.service.emailSender.impl

import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.service.emailSender.EmailServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PeluqueriaYaEmailSender(@Autowired val emailServiceAPI: EmailServiceAPI) {

    fun enviarMailDeConfirmacion(turno: Turno){
        val bodyBuilder = StringBuilder()
        createBody(bodyBuilder) {
            bodyBuilder.append("El turno ha sido confirmado!! Aproximadamente, en los próximos minutos el peluquero")
            bodyBuilder.appendln(" llegará a la ubicacion especificada.")
            bodyBuilder.appendln("Los datos de contacto del peluquero se encuentran en su pestaña de turnos, ante cualquier duda.")
        }

        emailServiceAPI.sendEmail(turno.getClienteEmail(),
                agregarNoReply("El turno fue confirmado"),
                bodyBuilder.toString()
        )
    }

    fun enviarMailAlClienteQueTerminoSuEspera(turno: Turno) {
        val bodyBuilder = StringBuilder()
        createBody(bodyBuilder) {
            bodyBuilder.appendln("El turno está en estado \"Pendiente\", eso quiere decir que esta cada vez mas cerca de ser atendido!")
        }

        emailServiceAPI.sendEmail(turno.getClienteEmail(),
                agregarNoReply("El turno ahora esta en Pendiente"),
                bodyBuilder.toString()
        )
    }

    fun enviarMailAlClienteQueSeCanceloElTurno(turno: Turno) {
        val bodyBuilder = StringBuilder()
        createBody(bodyBuilder) {
            bodyBuilder.appendln("El turno con el peluquero \"${turno.getPeluqueroName()}\" fue cancelado lamentablemente :C.")
            bodyBuilder.appendln("Puede pedir otro turno a otro peluquero o volver a intentar con el mismo si este está conectado!")
        }
        emailServiceAPI.sendEmail(turno.getClienteEmail(),
                agregarNoReply("El turno fue cancelado"),
                bodyBuilder.toString()
        )
    }

    fun enviarMailAlClienteQueSuTurnoEstaEnEspera(turno:Turno) {
        val bodyBuilder = StringBuilder()
        createBody(bodyBuilder) {
            bodyBuilder.appendln("Su turno fue ingresado en la cola de espera del peluquero porque este posee completa su casilla de turnos.")
            bodyBuilder.appendln("Es muy probable que su turno demore mucho. Si quiere puede cancelar el turno en su pestaña de turnos.")
        }

        emailServiceAPI.sendEmail(turno.getClienteEmail(),
                agregarNoReply("El turno esta en la cola de espera del peluquero \"${turno.getPeluqueroName()}\""),
                bodyBuilder.toString()
        )
    }

    fun enviarMailDeFinalizacionTurno(turno: Turno) {
        val bodyBuilder = StringBuilder()
        createBody(bodyBuilder) {
            bodyBuilder.appendln("El turno ha sido marcado como finalizado por el peluquero.")
            bodyBuilder.appendln("Puede calificar su turno en la opción de su perfil \"Mis turnos\", sección \"Historial\" para ofrecer un feedback del servicio contratado!")
            bodyBuilder.appendln("En caso de haber existido algun tipo de problema, contactenos al email peluqueria-ya-soporte@gmail.com.")
        }

        emailServiceAPI.sendEmail(turno.getClienteEmail(),
                agregarNoReply("El peluquero \"${turno.getPeluqueroName()}\" ha finalizado el turno, ya puede calificar el servicio"),
                bodyBuilder.toString()
        )
    }

    private fun agregarNoReply(str:String):String = "(No Reply) $str"

    private fun createBody(bodyBuilder:StringBuilder, middleBodyMessage: () -> Unit) {
        agregarSaludoEnBodyBuilder(bodyBuilder)
        middleBodyMessage()
        agregarAgradecimientoYSaludoEnBodyBuilder(bodyBuilder)
    }

    private fun agregarSaludoEnBodyBuilder(bodyBuilder: StringBuilder){
        bodyBuilder.appendln("Hola, ¿Qué tal?")
        bodyBuilder.appendln()
    }

    private fun agregarAgradecimientoYSaludoEnBodyBuilder(bodyBuilder: StringBuilder){
        bodyBuilder.appendln()
        bodyBuilder.appendln("Gracias por utilizar nuestro servicio!")
        bodyBuilder.appendln("Saludos! :D")
        bodyBuilder.appendln()
        bodyBuilder.appendln("Peluqueria Ya")
    }
}