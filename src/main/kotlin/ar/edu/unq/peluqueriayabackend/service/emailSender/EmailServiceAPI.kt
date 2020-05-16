package ar.edu.unq.peluqueriayabackend.service.emailSender

interface EmailServiceAPI {

    fun sendEmail(to:String, subject:String, text:String)

    fun sendMessageWithAttachment(
            to:String, subject:String, text:String, pathToAttachment:String)
}