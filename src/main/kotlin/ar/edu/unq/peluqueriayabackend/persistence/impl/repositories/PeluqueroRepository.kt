package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface PeluqueroRepository : JpaRepository<Peluquero, Int>{

    @Query("SELECT p FROM Peluquero p WHERE lower(p.nombre) LIKE lower(concat('%',?1,'%')) OR ?2 member of p.tipos")
    fun findAllLikeNameOrWithTipos(nombre: String, tipo: String, pageable: Pageable): Page<Peluquero>

}