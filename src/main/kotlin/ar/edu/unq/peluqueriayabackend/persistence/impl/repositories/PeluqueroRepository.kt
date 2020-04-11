package ar.edu.unq.peluqueriayabackend.persistence.impl.repositories

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import org.springframework.data.jpa.repository.JpaRepository

interface PeluqueroRepository : JpaRepository<Peluquero, Int>{

}