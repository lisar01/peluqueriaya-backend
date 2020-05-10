package ar.edu.unq.peluqueriayabackend.service.emailSender.impl

import ar.edu.unq.peluqueriayabackend.service.emailSender.EmailServiceAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.io.File

@Service
class EmailServiceImpl(@Autowired
                       private val emailSender:JavaMailSender):EmailServiceAPI {

    @Async("threadPoolTaskExecutor")
    override fun sendEmail(to: String, subject: String, text: String) {
        val message = SimpleMailMessage()
        message.setTo(to)
        message.setSubject(subject)
        message.setText(text)
        emailSender.send(message)
    }

    @Async("threadPoolTaskExecutor")
    override fun sendMessageWithAttachment(to: String, subject: String, text: String, pathToAttachment: String) {
        val message = emailSender.createMimeMessage()

        val helper = MimeMessageHelper(message, true)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(text)

        val file = FileSystemResource(File(pathToAttachment))
        helper.addAttachment("Invoice", file)

        emailSender.send(message)
    }
}