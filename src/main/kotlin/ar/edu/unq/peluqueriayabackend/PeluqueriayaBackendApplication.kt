package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.ServicioRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.math.BigDecimal


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
    }

    @Bean
    fun demo(peluqueroRepository: PeluqueroRepository, servicioRepository: ServicioRepository): CommandLineRunner? {
        return CommandLineRunner {
            println("|**!!INFO!!**| Descomente el metodo: createFakeData(..) en la clase PeluqueriayaBackendApplication, para generar datos falsos")
            println("|**!!ADVICE!!**| Una vez ejecutado el metodo createFakeData(..) vuelva a comentarlo o elimine los datos de la Base de Datos, para evitar errores")
            this.createFakeData(peluqueroRepository, servicioRepository)
        }
    }

    fun createFakeData(peluqueroRepository: PeluqueroRepository, servicioRepository: ServicioRepository) {
        println("|**!!WARNING!!**| Metodo createFakeData(..) en clase PeluqueriayaBackendApplication no comentado. Esto puede generar errores si los datos ya existen")

        val peluquero1 = Peluquero.Builder().withNombre("El barba").withDescripcion("El mejor barbero de zona sur!").withTipos(mutableSetOf(PeluqueroType.HOMBRE)).withCorteMin(BigDecimal(250)).withDistanciaMax(BigDecimal(5.5)).withEmail("barbamail@pepe.com").withUbicacion(Ubicacion("-34.706416", "-58.278559")).withEstado(PeluqueroState.DISPONIBLE).build()

        //Sin logo
        var peluquero2 = Peluquero.Builder().withLogo("https://i.pinimg.com/236x/3f/50/87/3f50871f2a2f132399894dfb4f9c73cf.jpg").withNombre("La pelu").withDescripcion("La mejor peluquera de zona sur!").withTipos(mutableSetOf(PeluqueroType.MUJER, PeluqueroType.HOMBRE, PeluqueroType.CHICOS)).withCorteMin(BigDecimal(250)).withDistanciaMax(BigDecimal(7)).withEmail("lapelu@gmail.com").withUbicacion(Ubicacion("-34.725524", "-58.244012")).withEstado(PeluqueroState.DISPONIBLE).build()

        var peluquero3 = Peluquero.Builder().withLogo("https://image.shutterstock.com/image-vector/barber-shop-logo-260nw-672396868.jpg").withNombre("Pepe el barbero").withDescripcion("Soy pepe grillo el mejor barbero!").withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.CHICOS)).withCorteMin(BigDecimal(400)).withDistanciaMax(BigDecimal(3)).withEmail("pepe@yahoo.com").withUbicacion(Ubicacion("-34.722186", "-58.256462")).withEstado(PeluqueroState.OCUPADO).build()

        val peluquero4MDQ = Peluquero.Builder().withNombre("El barba de mardel").withDescripcion("Soy el mejor barbero de Mar del Plata, despues de la playa cortate conmigo!").withTipos(mutableSetOf(PeluqueroType.HOMBRE, PeluqueroType.CHICOS)).withCorteMin(BigDecimal(100)).withDistanciaMax(BigDecimal(10)).withEmail("barbamdq@mdq.com").withUbicacion(Ubicacion("-38.005004", "-57.542606")).withEstado(PeluqueroState.DISPONIBLE).build()

        var peluquero5MDQ = Peluquero.Builder().withLogo("https://d1yjjnpx0p53s8.cloudfront.net/styles/logo-thumbnail/s3/0024/5278/brand.gif?itok=pw1PLx5N").withNombre("La pelu de mardel").withDescripcion("La mejor pelu de mar del plata, el mejor alisado... despreocupate!").withTipos(mutableSetOf(PeluqueroType.MUJER)).withCorteMin(BigDecimal(300)).withDistanciaMax(BigDecimal(5)).withEmail("laPelu@mdq.com").withUbicacion(Ubicacion("-38.003655", "-57.554497")).withEstado(PeluqueroState.OCUPADO).build()

        //Guardando peluqueros creados

        peluqueroRepository.save(peluquero1)
        peluqueroRepository.save(peluquero4MDQ)

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
                .withDescripcion("Corte de barba prolijo")
                .withTipos(mutableSetOf(ServicioType.CORTE, ServicioType.BARBA))
                .withPrecio(BigDecimal(120)).withPeluquero(peluquero3).build()

        val servicioTenir = Servicio.Builder()
                .withDescripcion("Teñido con los mejores productos")
                .withTipos(mutableSetOf(ServicioType.TENIDO))
                .withPrecio(BigDecimal(800))
                .withPeluquero(peluquero3).build()

        val servicioTratamientoCapilar = Servicio.Builder()
                .withDescripcion("Tratamiento capilar que resuelve problemas de caspa")
                .withTipos(mutableSetOf(ServicioType.TRATAMIENTO))
                .withPrecio(BigDecimal(2000)).withPeluquero(peluquero5MDQ).build()

        val servicioPeinadoDeFiesta = Servicio.Builder()
                .withDescripcion("Peinado de fiesta siguiendo últimas tendencias")
                .withTipos(mutableSetOf(ServicioType.PEINADO, ServicioType.RECOGIDO))
                .withPrecio(BigDecimal(1000)).withPeluquero(peluquero5MDQ).build()

        val servicioUnia = Servicio.Builder()
                .withDescripcion("Pintado de uñas con los colores de la temporada")
                .withTipos(mutableSetOf(ServicioType.UNAS))
                .withPrecio(BigDecimal(200)).withPeluquero(peluquero2).build()

        servicioRepository.saveAll(listOf(servicioBarba, servicioTenir, servicioTratamientoCapilar,
                servicioPeinadoDeFiesta, servicioUnia))
    }

}

fun main(args: Array<String>) {
    runApplication<PeluqueriayaBackendApplication>(*args)
}
