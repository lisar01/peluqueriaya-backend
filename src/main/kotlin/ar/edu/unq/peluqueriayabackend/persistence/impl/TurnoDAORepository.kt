package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.persistence.TurnoDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.TurnoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TurnoDAORepository(@Autowired val turnoRepository: TurnoRepository) : TurnoDAO {

    override fun get(id: Long): Optional<Turno> {
        return turnoRepository.findById(id)
    }

    override fun getAll(): Collection<Turno> {
        return turnoRepository.findAll()
    }

    override fun save(t: Turno): Turno {
        return turnoRepository.save(t)
    }

    override fun update(t: Turno): Turno {
        return save(t)
    }

    override fun delete(id: Long) {
        //TODO
        // SIN IMPLEMENTAR
        // NO HACE NADA POR AHORA
    }

    override fun peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean {
        return turnoRepository.peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero, valor)
    }
}