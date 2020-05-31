package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.controller.dtos.Filtro
import ar.edu.unq.peluqueriayabackend.exception.PeluqueroNoSePuedeDesconectar
import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.TurnoDAO
import ar.edu.unq.peluqueriayabackend.service.PeluqueroService
import ar.edu.unq.peluqueriayabackend.service.emailSender.impl.PeluqueriaYaEmailSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class PeluqueroServiceImpl(
        @Autowired val peluqueroDAO: PeluqueroDAO,
        @Autowired val turnoDAO: TurnoDAO,
        @Autowired val peluqueriaYaEmailSender: PeluqueriaYaEmailSender

): PeluqueroService {

    override fun get(id: Long): Optional<Peluquero> {
        return peluqueroDAO.get(id)
    }

    override fun getAll(): Collection<Peluquero> {
        return peluqueroDAO.getAll()
    }

    @Transactional
    override fun save(t: Peluquero): Peluquero {
        return peluqueroDAO.save(t)
    }

    @Transactional
    override fun update(t: Peluquero): Peluquero {
        return peluqueroDAO.update(t)
    }

    override fun getByEmail(emailPeluquero: String): Optional<Peluquero> {
        return peluqueroDAO.getByEmail(emailPeluquero)
    }

    //No va con @transactional porque no persiste nada, solo consulta datos
    override fun buscar(ubicacion: Ubicacion, filtro: Filtro?, pageable: Pageable): Page<Peluquero> {
                return peluqueroDAO.findAllByUbicacionCercanaAndNombreLikeAndContainsTipoAndContainsTipoDeServicion(5.3,
                ubicacion.getLongitudeAsDouble(), ubicacion.getLatitudeAsDouble(),
                filtro?.nombre, filtro?.tipos, filtro?.tipoDeServicio, pageable)
    }

    @Transactional
    override fun desconectar(peluquero: Peluquero) : Peluquero {
        peluquero.desconectar()

        if(!peluquero.getEstaDesconectado())
            throw PeluqueroNoSePuedeDesconectar()

        //Cancela todos los turnos en espera y pendientes del peluquero si se desconecto
        val turnosEnEsperaOPendientes:List<Turno> = turnoDAO.findAllByEstadoPendienteOEspera(peluquero)

        turnosEnEsperaOPendientes.forEach {
            it.cancelar()
            turnoDAO.update(it)
            peluqueriaYaEmailSender.enviarMailAlClienteQueSeCanceloElTurno(it)
        }

        return peluqueroDAO.save(peluquero)
    }

    @Transactional
    override fun conectar(peluquero: Peluquero): Peluquero {
        peluquero.conectar()
        return peluqueroDAO.save(peluquero)
    }
}