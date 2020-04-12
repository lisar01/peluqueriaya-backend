package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.math.BigDecimal

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
			println("Descomente el metodo: createFakeData(..) en la clase PeluqueriayaBackendApplication, para generar datos falsos")
			println("|**!!ADVICE!!**| Una vez ejecutado el metodo createFakeData(..) vuelva a comentarlo o elimine los datos de la Base de Datos, para evitar errores")
			this.createFakeData(peluqueroRepository)

		}
	}

	fun createFakeData(peluqueroRepository: PeluqueroRepository) {
		println("|**!!WARNING!!**| Metodo createFakeData(..) en clase PeluqueriayaBackendApplication no comentado. Esto puede generar errores si los datos ya existen")

		val peluquero1:Peluquero = Peluquero("https://image.shutterstock.com/image-vector/men-barbershop-hairstylist-banner-logo-260nw-1315338812.jpg",
				"El barba", BigDecimal(250), BigDecimal(5.5),"unmail@gmail.com",
				Ubicacion("-34.706416","-58.278559"),PeluqueroState.DISPONIBLE, emptyList())


		peluqueroRepository.save(peluquero1)


	}

}

fun main(args: Array<String>) {
	runApplication<PeluqueriayaBackendApplication>(*args)
}
