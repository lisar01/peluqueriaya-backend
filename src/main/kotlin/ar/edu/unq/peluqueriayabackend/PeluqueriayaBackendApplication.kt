package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.Servicio
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ServicioRepository
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
	fun demo(peluqueroRepository: PeluqueroRepository, servicioRepository: ServicioRepository): CommandLineRunner? {
		return CommandLineRunner { args: Array<String?>? ->
			println("Descomente el metodo: createFakeData(..) en la clase PeluqueriayaBackendApplication, para generar datos falsos")
			println("|**!!ADVICE!!**| Una vez ejecutado el metodo createFakeData(..) vuelva a comentarlo o elimine los datos de la Base de Datos, para evitar errores")
			this.createFakeData(peluqueroRepository,servicioRepository)

		}
	}

	fun createFakeData(peluqueroRepository: PeluqueroRepository, servicioRepository: ServicioRepository) {
		println("|**!!WARNING!!**| Metodo createFakeData(..) en clase PeluqueriayaBackendApplication no comentado. Esto puede generar errores si los datos ya existen")

		val peluquero1 = Peluquero.Builder().
				withNombre("El barba").
				logo("https://image.shutterstock.com/image-vector/men-barbershop-hairstylist-banner-logo-260nw-1315338812.jpg").
				withCorteMin(BigDecimal(250)).
				withDistanciaMax(BigDecimal(5.5)).
				withEmail("barbamail@pepe.com").
				withUbicacion(Ubicacion("-34.706416","-58.278559")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()

		//Sin logo
		var peluquero2 = Peluquero.Builder().
				logo("https://i.pinimg.com/236x/3f/50/87/3f50871f2a2f132399894dfb4f9c73cf.jpg").
				withNombre("La pelu").
				withCorteMin(BigDecimal(250)).
				withDistanciaMax(BigDecimal(7)).
				withEmail("lapelu@gmail.com").
				withUbicacion(Ubicacion("-34.725524", "-58.244012")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()



		var peluquero3 = Peluquero.Builder().
				logo("https://image.shutterstock.com/image-vector/barber-shop-logo-260nw-672396868.jpg").
				withNombre("Pepe el barbero").
				withCorteMin(BigDecimal(400)).
				withDistanciaMax(BigDecimal(3)).
				withEmail("pepe@yahoo.com").
				withUbicacion(Ubicacion("-34.722186", "-58.256462")).
				withEstado(PeluqueroState.OCUPADO).
				build()

		peluqueroRepository.save(peluquero1)
		peluquero2 = peluqueroRepository.save(peluquero2)
		peluquero3 = peluqueroRepository.save(peluquero3)

		val service1 = Servicio.Builder().
				withNombre("Barba").
				withPrecio(BigDecimal(100)).
				withPeluquero(peluquero3).
				build()

		servicioRepository.save(service1)



	}

}

fun main(args: Array<String>) {
	runApplication<PeluqueriayaBackendApplication>(*args)
}
