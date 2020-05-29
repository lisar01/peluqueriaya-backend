package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ClienteRepository
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ServicioRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.Resource
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.DatabasePopulator
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.math.BigDecimal
import javax.sql.DataSource


@SpringBootApplication
@EnableJpaAuditing
@PropertySource("classpath:heremaps.properties")
class PeluqueriayaBackendApplication : WebMvcConfigurer {

	override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/")
	}
	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:3000")
				.allowCredentials(true)
	}

	// Corre el script .sql para crear la funcion del calculo de distancia
	@Value("classpath:createDistanceFunction.sql")
	lateinit var dataScript: Resource

	@Bean
	fun createFunctionDB(dataSource: DataSource):DataSourceInitializer {
		val initializer = DataSourceInitializer()
		initializer.setDataSource(dataSource)
		initializer.setDatabasePopulator(databasePopulator())
		return initializer
	}

	private fun databasePopulator(): DatabasePopulator {

		val populator = ResourceDatabasePopulator()
		populator.addScript(dataScript)
		return populator
	}

    @Bean
    fun demo(peluqueroRepository: PeluqueroRepository, servicioRepository: ServicioRepository, clienteRepository: ClienteRepository): CommandLineRunner? {
        return CommandLineRunner {
            println("|**!!INFO!!**| Descomente el metodo: createFakeData(..) en la clase PeluqueriayaBackendApplication, para generar datos falsos")
            println("|**!!ADVICE!!**| Una vez ejecutado el metodo createFakeData(..) vuelva a comentarlo o elimine los datos de la Base de Datos, para evitar errores")
            this.createFakeData(peluqueroRepository, servicioRepository, clienteRepository)
        }
    }

    fun createFakeData(peluqueroRepository: PeluqueroRepository, servicioRepository: ServicioRepository, clienteRepository: ClienteRepository) {
        println("|**!!WARNING!!**| Metodo createFakeData(..) en clase PeluqueriayaBackendApplication no comentado. Esto puede generar errores si los datos ya existen")

		val clientePepe = Cliente.Builder().
							withNombre("Pepe").
							withApellido("Grillo").
							withEmail("cassanojoseluis@gmail.com").
							withImgPerfil("https://vignette.wikia.nocookie.net/disney/images/f/f0/Profile_-_Jiminy_Cricket.jpeg/revision/latest?cb=20190312063605").
							withUbicacion(Ubicacion("-34.721999", "-58.250447")).
							withNroTelefono("1100001111").
							build()

		clienteRepository.save(clientePepe)

        val peluquero1 = Peluquero.Builder().
				withNombre("El barba").
				withDescripcion("El mejor barbero de zona sur!").
				withTipos(mutableSetOf(PeluqueroType.HOMBRE)).
				withCorteMin(BigDecimal(250)).
				withDistanciaMax(BigDecimal(6)).
				withEmail("lisar.3467@gmail.com").
				withUbicacion(Ubicacion("-34.706416", "-58.278559")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()

        //Sin logo
        var peluquero2 = Peluquero.Builder().
				withLogo("https://i.pinimg.com/236x/3f/50/87/3f50871f2a2f132399894dfb4f9c73cf.jpg").
				withNombre("La pelu").
				withDescripcion("La mejor peluquera de zona sur!").
				withTipos(mutableSetOf(PeluqueroType.MUJER, PeluqueroType.KIDS)).
				withCorteMin(BigDecimal(250)).withDistanciaMax(BigDecimal(7)).
		        withEmail("cassanojoseluis@gmail.com").
				withUbicacion(Ubicacion("-34.725524", "-58.244012")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()

        var peluquero3 = Peluquero.Builder().
				withLogo("https://image.shutterstock.com/image-vector/barber-shop-logo-260nw-672396868.jpg").
				withNombre("Pepe el barbero").
				withDescripcion("Soy pepe grillo el peluquero con los mejores cortes!").
				withTipos(mutableSetOf(PeluqueroType.MUJER, PeluqueroType.KIDS)).
				withCorteMin(BigDecimal(400)).
				withDistanciaMax(BigDecimal(6)).
				withEmail("pepe@yahoo.com").
				withUbicacion(Ubicacion("-34.722186", "-58.256462")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()

        val peluquero4MDQ = Peluquero.Builder().
				withNombre("El barba de mardel").
				withDescripcion("Soy el mejor barbero de Mar del Plata, despues de la playa cortate conmigo!").
				withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.KIDS)).
				withCorteMin(BigDecimal(100)).
				withDistanciaMax(BigDecimal(10)).
				withEmail("barbamdq@mdq.com").
				withUbicacion(Ubicacion("-38.005004", "-57.542606")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()

        var peluquero5MDQ = Peluquero.Builder().
				withLogo("https://d1yjjnpx0p53s8.cloudfront.net/styles/logo-thumbnail/s3/0024/5278/brand.gif?itok=pw1PLx5N").
				withNombre("La pelu de mardel").
				withDescripcion("La mejor pelu de mar del plata, el mejor alisado... despreocupate!").
				withTipos(mutableSetOf(PeluqueroType.MUJER)).withCorteMin(BigDecimal(300)).
				withDistanciaMax(BigDecimal(6)).
				withEmail("laPelu@mdq.com").
				withUbicacion(Ubicacion("-38.003655", "-57.554497")).
				withEstado(PeluqueroState.DISPONIBLE).
				build()

		//Sin logo
		val peluquero6 = Peluquero.Builder().
		withNombre("Lo pibitos barberia").
		withDescripcion("La barberia esencial para cualquier pibito").
		withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.KIDS)).
		withCorteMin(BigDecimal(200)).
		withDistanciaMax(BigDecimal(6)).
		withEmail("lopibito@gmail.com").
		withUbicacion(Ubicacion("-34.722486", "-58.256462")).
		withEstado(PeluqueroState.DISPONIBLE).
		build()

		val peluquero7 = Peluquero.Builder().
		withLogo("https://www.bue360.com/media/com_jbusinessdirectory/pictures/companies/9603/SizoGerard-1492191444.jpg").
		withNombre("Ziso").
		withDescripcion("Peluqueria Unisex, lo que quieras lo tenes!").
		withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.MUJER)).
		withCorteMin(BigDecimal(9000)).
		withDistanciaMax(BigDecimal(7.2)).
		withEmail("zisoooo@gmail.com").
		withUbicacion(Ubicacion("-34.722486", "-58.256462")).
		withEstado(PeluqueroState.DISPONIBLE).
		build()

		val peluquero8 = Peluquero.Builder().
		withLogo("https://image.freepik.com/free-vector/gentleman-barber-shop-logo_96485-97.jpg").
		withNombre("El rapador").
		withDescripcion("Promo por rapado!").
		withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.KIDS, PeluqueroType.MUJER)).
		withCorteMin(BigDecimal(80.5)).
		withDistanciaMax(BigDecimal(6)).
		withEmail("el-rapador777@gmail.com").
		withUbicacion(Ubicacion("-34.722486", "-58.256462")).
		withEstado(PeluqueroState.DISPONIBLE).
		build()

		val peluquero9 = Peluquero.Builder().
		withLogo("https://www.onlinelogomaker.com/blog/wp-content/uploads/2017/08/barber-shop-logo.jpg").
		withNombre("Pilot").
		withDescripcion("Probando 1..2..3!").
		withTipos(mutableSetOf(PeluqueroType.HOMBRE)).
		withCorteMin(BigDecimal(150.50)).
		withDistanciaMax(BigDecimal(6)).
		withEmail("pilot213123213@gmail.com").
		withUbicacion(Ubicacion("-34.722486", "-58.256462")).
		withEstado(PeluqueroState.DISPONIBLE).
		build()

		val peluquero10 = Peluquero.Builder().
		withLogo("https://i.pinimg.com/originals/d4/67/6f/d4676f6f2ff10d7499b150cb74374ca1.jpg").
		withNombre("Manos de tijeras").
		withDescripcion("El mejor con la tijera!").
		withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.KIDS, PeluqueroType.MUJER)).
		withCorteMin(BigDecimal(250)).
		withDistanciaMax(BigDecimal(7)).
		withEmail("manopla-de-tijera889@gmail.com").
		withUbicacion(Ubicacion("-34.722486", "-58.256462")).
		withEstado(PeluqueroState.DISPONIBLE).
		build()

        //Guardando peluqueros creados

        peluqueroRepository.save(peluquero1)
        peluqueroRepository.save(peluquero4MDQ)

		peluqueroRepository.save(peluquero6)
		peluqueroRepository.save(peluquero7)
		peluqueroRepository.save(peluquero8)
		peluqueroRepository.save(peluquero9)
		peluqueroRepository.save(peluquero10)

        peluquero2 = peluqueroRepository.save(peluquero2)
        peluquero3 = peluqueroRepository.save(peluquero3)
        peluquero5MDQ = peluqueroRepository.save(peluquero5MDQ)

        //Creando servicios a peluqueros
        crearServiciosDePeluqueros(servicioRepository, peluquero2, peluquero3, peluquero5MDQ)

        println("|**!!INFO!!**| Metodo de crear datos falsos finalizado!")
    }

    private fun crearServiciosDePeluqueros(servicioRepository: ServicioRepository, peluquero2: Peluquero, peluquero3: Peluquero,
                                           peluquero5MDQ: Peluquero) {
        val servicioBarba = Servicio.Builder()
                .withNombre("Corte de barba prolijo")
                .withTipos(mutableSetOf(ServicioType.CORTE, ServicioType.BARBERIA))
                .withPrecio(BigDecimal(120)).withPeluquero(peluquero3).build()

        val servicioTenir = Servicio.Builder()
                .withNombre("Teñido con los mejores productos")
                .withTipos(mutableSetOf(ServicioType.TENIDO))
                .withPrecio(BigDecimal(800))
                .withPeluquero(peluquero3).build()

        val servicioTratamientoCapilar = Servicio.Builder()
                .withNombre("Tratamiento capilar que resuelve problemas de caspa")
                .withTipos(mutableSetOf(ServicioType.TRATAMIENTO))
                .withPrecio(BigDecimal(2000)).withPeluquero(peluquero5MDQ).build()

        val servicioPeinadoDeFiesta = Servicio.Builder()
                .withNombre("Peinado de fiesta siguiendo últimas tendencias")
                .withTipos(mutableSetOf(ServicioType.PEINADO, ServicioType.RECOGIDO))
                .withPrecio(BigDecimal(1000)).withPeluquero(peluquero5MDQ).build()

        val servicioUnia = Servicio.Builder()
                .withNombre("Pintado de uñas con los colores de la temporada")
                .withTipos(mutableSetOf(ServicioType.UNAS))
                .withPrecio(BigDecimal(200)).withPeluquero(peluquero2).build()

        servicioRepository.saveAll(listOf(servicioBarba, servicioTenir, servicioTratamientoCapilar,
                servicioPeinadoDeFiesta, servicioUnia))
    }

}

fun main(args: Array<String>) {
    runApplication<PeluqueriayaBackendApplication>(*args)
}
