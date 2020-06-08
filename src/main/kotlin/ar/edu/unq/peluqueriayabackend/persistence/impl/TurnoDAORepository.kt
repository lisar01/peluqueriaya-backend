package ar.edu.unq.peluqueriayabackend.persistence.impl

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.model.TurnoState
import ar.edu.unq.peluqueriayabackend.persistence.TurnoDAO
import ar.edu.unq.peluqueriayabackend.persistence.impl.repositories.TurnoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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

    override fun peluqueroPoseeAlgunTurnoConfirmado(peluquero: Peluquero): Boolean {
        return turnoRepository.peluqueroPoseeAlgunTurnoConfirmado(peluquero)
    }

    override fun peluqueroPoseeCantidadDeTurnosEnEsperaMayorOIgualA(peluquero: Peluquero, valor: Long): Boolean {
        return turnoRepository.peluqueroPoseeCantidadDeTurnosEnEsperaMayorOIgualA(peluquero, valor)
    }

    override fun findTurnoEnEsperaMasAntiguoDelPeluquero(peluquero: Peluquero): Optional<Turno> {
        val resultList = turnoRepository.findTurnosEnEsperaDelPeluqueroOrdenadoPor(peluquero, PageRequest.of(0,1,Sort.by(Sort.Direction.ASC, "fechaInicio"))).content
        var result = Optional.empty<Turno>()
        if(resultList.isNotEmpty()){
            result = Optional.of(resultList[0])
        }
        return result
    }

    override fun findAllByEstadoPendienteOEspera(peluquero: Peluquero): List<Turno> {
        return turnoRepository.findAllByEstadoPendienteOEspera(peluquero)
    }

    override fun findAllConPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno> {
        return turnoRepository.findAllByPeluquero(peluquero,pageable)
    }

    override fun findAllConPeluqueroYEstadoFinalizado(peluquero: Peluquero, pageable: Pageable): Page<Turno> {
        return turnoRepository.findAllByPeluqueroAndEstadoTurno(peluquero, TurnoState.FINALIZADO, pageable)
    }

    override fun findAllConPeluqueroYEstadoPendientesOConfirmados(peluquero: Peluquero, pageable: Pageable): Page<Turno> {
        return turnoRepository.findAllByPeluqueroAndEstadoTurnoConfirmadoOPendiente(peluquero,pageable)
    }

    override fun obtenerPromedioPuntuacionDeLosTurnosConPeluquero(peluquero: Peluquero): Double {
        return turnoRepository.obtenerPromedioPuntuacionDeLosTurnosConPeluquero(peluquero)
    }
}