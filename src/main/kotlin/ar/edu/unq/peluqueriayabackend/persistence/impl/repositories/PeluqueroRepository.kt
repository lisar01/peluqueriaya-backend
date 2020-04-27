package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface PeluqueroRepository : JpaRepository<Peluquero, Int>{

    @Query("SELECT p FROM Peluquero p WHERE distance(cast(p.ubicacion.latitude as double),cast(p.ubicacion.longitude as double),?2,?3) <= ?1")
    fun findAllInRangeAtCoordinates(rangoEnKm: Double,lat:Double,lng: Double, pageable: Pageable): Page<Peluquero>

    @Query("SELECT p FROM Peluquero p WHERE distance(cast(p.ubicacion.latitude as double),cast(p.ubicacion.longitude as double),?2,?3) <= ?1 AND lower(p.nombre) LIKE lower(concat('%',?4,'%')) OR ?5 member of p.tipos")
    fun findAllInRangeAtCoordinatesAndLikeNameOrWithTipos(rangoEnKm:Double,lat:Double,lng:Double, nombre: String,tipo:PeluqueroType, pageable: Pageable): Page<Peluquero>

    @Query("SELECT p FROM Peluquero p WHERE distance(cast(p.ubicacion.latitude as double),cast(p.ubicacion.longitude as double),?2,?3) <= ?1 AND lower(p.nombre) LIKE lower(concat('%',?4,'%'))")
    fun findAllInRangeAtCoordinatesAndLikeName(rangoEnKm: Double, latitude: Double, longitude: Double, nombre: String, pageable: Pageable): Page<Peluquero>

}