package ar.edu.unq.peluqueriayabackend.exception

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@Suppress("unused")
data class APIError(val message: String,
                    @JsonIgnore
                    val httpStatus: HttpStatus,
                    val path: String? = "",
                    val subErrors: List<String?> = listOf()) {

    val timestamp: LocalDateTime get() = LocalDateTime.now()

    val error get() = this.httpStatus.reasonPhrase

    val status get() = this.httpStatus.value()

}
