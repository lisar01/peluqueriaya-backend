package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroRepository
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PeluqueroServiceImpl(@Autowired val peluqueroDAO: PeluqueroRepository): PeluqueroService {
}