package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.PeluqueroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class PeluqueroDAORepository(@Autowired val peluqueroRepository: PeluqueroRepository):PeluqueroDAO{

    override fun get(id: Int): Optional<Peluquero> {
        return peluqueroRepository.findById(id)
    }

    override fun getAll(): Collection<Peluquero> {
        return peluqueroRepository.findAll()
    }

    override fun save(t: Peluquero): Peluquero {
        return peluqueroRepository.save(t)
    }

    override fun update(t: Peluquero): Peluquero {
        return save(t)
    }

    // Solo setea el estado en deshabilitado (No borra nada)
    override fun delete(id: Int) {
        val peluqueroDeshabilitado = get(id).get()
        peluqueroDeshabilitado.estado = PeluqueroState.DESHABILITADO
        save(peluqueroDeshabilitado)
    }

    override fun buscarPeluquerosEnUbicacionDentroDelRadioEnKm(distanciaEnKm: Double,ubicacion: Ubicacion,pageable:Pageable): Page<Peluquero> {
        return peluqueroRepository.findAllInRangeAtCoordinates(
                distanciaEnKm,
                ubicacion.getLatitudeAsDouble(),
                ubicacion.getLongitudeAsDouble(),
                pageable)
    }

    override fun buscarPeluquerosConNombreOTipoYQueEstenDentroDelRadioEnKmDeLaUbicacion(nombreOTipo:String, distanciaEnKm: Double,ubicacion:Ubicacion,pageable: Pageable): Page<Peluquero> {
        // Si el argumento nombreOTipo no matchea con un enum existente, solo se busca por nombre
        val tipo: PeluqueroType
        try{
            tipo = encontrarTipo(nombreOTipo)
        }catch (e: Exception){
            return peluqueroRepository.findAllInRangeAtCoordinatesAndLikeName(
                    distanciaEnKm,
                    ubicacion.getLatitudeAsDouble(),
                    ubicacion.getLongitudeAsDouble(),
                    nombreOTipo,
                    pageable
                    )
        }

        return peluqueroRepository.findAllInRangeAtCoordinatesAndLikeNameOrWithTipos(
                distanciaEnKm,
                ubicacion.getLatitudeAsDouble(),
                ubicacion.getLongitudeAsDouble(),
                nombreOTipo,
                tipo,
                pageable
        )
    }

    override fun buscarPeluquerosPorTipoDeServicioYQueEstenDentroDelRadioEnKmDeLaUbicacion(tipoDeServicio: ServicioType, distanciaEnKm: Double, ubicacion:Ubicacion, pageable: Pageable): Page<Peluquero> {
        return peluqueroRepository.findAllPeluquerosInRangeAtCoordinatesAndWithTipoDeServicio(
                distanciaEnKm,
                ubicacion.getLatitudeAsDouble(),
                ubicacion.getLongitudeAsDouble(),
                tipoDeServicio,
                pageable)
    }

    override fun findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(distanciaMaxima: Double, longitud: Double, latitud: Double, nombre: String?, tipo: PeluqueroType?, tipoDeServicio: ServicioType?, pageable: Pageable): Page<Peluquero> {
        return peluqueroRepository.findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(distanciaMaxima, longitud, latitud, nombre, tipo, tipoDeServicio, pageable)
    }

    private fun encontrarTipo(input:String):PeluqueroType{
        val tipo = input.trim().toUpperCase()

        if(tipo.contains("MUJER"))
            return PeluqueroType.MUJER

        if(tipo.contains("HOMBRE"))
            return PeluqueroType.HOMBRE

        if(tipo.contains("CHICO").or(tipo.contains("NENE")).or(tipo.contains("NENA")).
                or(tipo.contains("CHIQUE")))
            return PeluqueroType.KIDS

        return PeluqueroType.valueOf(tipo)
    }
}