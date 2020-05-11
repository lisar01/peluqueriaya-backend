package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.exception.LimiteDeTurnosSimultaneosDelPeluqueroException
import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.TurnoDAO
import ar.edu.unq.peluqueriayabackend.service.TurnoService
import ar.edu.unq.peluqueriayabackend.service.emailSender.impl.PeluqueriaYaEmailSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class TurnoServiceImpl(
        @Autowired val turnoDAO: TurnoDAO,
        @Autowired val peluqueriaYaEmailSender: PeluqueriaYaEmailSender) : TurnoService {

    override fun get(id: Long): Optional<Turno> {
        return turnoDAO.get(id)
    }

    override fun getAll(): Collection<Turno> {
        return turnoDAO.getAll()
    }

    @Transactional
    override fun save(t: Turno): Turno {
        return turnoDAO.save(t)
    }

    @Transactional
    override fun update(t: Turno): Turno {
        return turnoDAO.update(t)
    }

    @Transactional
    override fun pedirTurno(cliente: Cliente, peluquero: Peluquero, serviciosSolicitadosInfo: List<ServicioInfo>): Turno {

        // Si el peluquero ya posee 3 turnos (Pendiente o Confirmado), no se permite crear el turno.
        val limiteDeTurnosSimultaneos: Long = 3
        if(turnoDAO.peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero, limiteDeTurnosSimultaneos))
            throw LimiteDeTurnosSimultaneosDelPeluqueroException(peluquero.id,limiteDeTurnosSimultaneos)

        val turno = Turno.Builder().
                        withCliente(cliente).
                        withPeluquero(peluquero).
                        withCorteMinInfo(peluquero.corteMin).
                        build()

        serviciosSolicitadosInfo.forEach { sInfo -> sInfo.turno = turno }
        turno.serviciosSolicitadosInfo = serviciosSolicitadosInfo.toMutableList()

        return turnoDAO.save(turno)
    }

    @Transactional
    override fun confirmarTurno(turno: Turno): Turno {
        turno.confirmarTurno()

        //Cada vez que confirma un turno se vuelve a setear el estado del peluquero en OCUPADO
        turno.peluquero.estado = PeluqueroState.OCUPADO

        val turnoUpdated = turnoDAO.update(turno)
        peluqueriaYaEmailSender.enviarMailDeConfirmacion(turno)
        return turnoUpdated
    }

    @Transactional
    override fun finalizarTurno(turno: Turno): Turno {
        turno.finalizarTurno()

        //Si el peluquero ya no posee turnos confirmados, su estado debe ser DISPONIBLE
        if(! turnoDAO.peluqueroPoseeAlgunTurnoConfirmado(turno.peluquero)){
            turno.peluquero.estado = PeluqueroState.DISPONIBLE
        }

        return turnoDAO.update(turno)
    }
}