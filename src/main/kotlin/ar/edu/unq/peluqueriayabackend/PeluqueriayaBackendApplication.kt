package ar.edu.unq.peluqueriayabackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
@EnableJpaAuditing
@Import(SwaggerConfiguration::class)
class PeluqueriayaBackendApplication : WebMvcConfigurer{

	// Go to "http://localhost:8080/swagger-ui.html" for use swagger ui

	override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/")
	}

}

fun main(args: Array<String>) {
	runApplication<PeluqueriayaBackendApplication>(*args)
}
