package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
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

	@Bean
	fun demo(peluqueroRepository: PeluqueroRepository): CommandLineRunner? {
		return CommandLineRunner { args: Array<String?>? ->
			System.out.println("Descomente el metodo: createFakeData(..) en la clase PeluqueriayaBackendApplication, para generar datos falsos")
			System.out.println("|**!!ADVICE!!**| Una vez ejecutado el metodo createFakeData(..) vuelva a comentarlo o elimine los datos de la Base de Datos, para evitar errores")
			this.createFakeData(peluqueroRepository)

		}
	}

	fun createFakeData(peluqueroRepository: PeluqueroRepository) {
		System.out.println("|**!!WARNING!!**| Metodo createFakeData(..) en clase PeluqueriayaBackendApplication no comentado esto puede generar errores si los datos ya existen")

	}

}

fun main(args: Array<String>) {
	runApplication<PeluqueriayaBackendApplication>(*args)
}
