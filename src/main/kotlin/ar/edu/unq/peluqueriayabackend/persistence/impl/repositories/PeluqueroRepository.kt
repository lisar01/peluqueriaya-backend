package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.ServicioType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

const val esPeluqueroCercano = "distance(cast(p.ubicacion.latitude as double),cast(p.ubicacion.longitude as double), :latitud, :longitud) <= :distanciaMaxima"
const val contieneTipo = "((:tipos) is null OR tipos in (:tipos))"
const val tieneNombre = "(:nombre is null OR UPPER(p.nombre) LIKE UPPER(concat('%',cast(:nombre as string),'%')))"
const val contieneAlgunServicioConTipo = "(:tipoDeServicio is null OR :tipoDeServicio member of s.tipos)"


@Repository
interface PeluqueroRepository : JpaRepository<Peluquero, Int> {

    @Query(value= "SELECT DISTINCT p FROM Peluquero p LEFT JOIN p.servicios s LEFT JOIN p.tipos tipos WHERE $esPeluqueroCercano AND $tieneNombre AND $contieneTipo AND $contieneAlgunServicioConTipo")
    fun findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(
            @Param("distanciaMaxima") distanciaMaxima: Double,
            @Param("longitud") longitud: Double,
            @Param("latitud") latitud: Double,
            @Param("nombre") nombre: String?,
            @Param("tipos") tipos: List<PeluqueroType>?,
            @Param("tipoDeServicio") tipoDeServicio: ServicioType?,
            pageable: Pageable): Page<Peluquero>
}