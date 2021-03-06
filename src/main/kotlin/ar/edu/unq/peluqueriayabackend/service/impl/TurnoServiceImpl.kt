package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.exception.*
import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.TurnoDAO
import ar.edu.unq.peluqueriayabackend.service.TurnoService
import ar.edu.unq.peluqueriayabackend.service.emailSender.impl.PeluqueriaYaEmailSender
import ar.edu.unq.peluqueriayabackend.service.geodistance.GeoDistanceServiceApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*
import javax.transaction.Transactional

@Service
class TurnoServiceImpl(
        @Autowired val turnoDAO: TurnoDAO,
        @Autowired val peluqueriaYaEmailSender: PeluqueriaYaEmailSender,
        @Autowired val geoDistanceServiceApi: GeoDistanceServiceApi) : TurnoService {

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
    override fun pedirTurno(cliente: Cliente, peluquero: Peluquero, serviciosSolicitadosInfo: List<ServicioInfo>, ubicacion: Ubicacion): Turno {

        if(distanciaEnRangoDelPeluqueroEstaExcedida(ubicacion, peluquero))
            throw DistanciaMaximaDelPeluqueroExcedida()

        if(peluquero.getEstaDesconectado() || peluquero.getEstaDeshabilitado())
            throw PeluqueroDesconectado()

        if(! peluquero.getTieneTurnosEnEspera())
            throw PeluqueroNoPoseeMasTurnos()

        val turno = Turno.Builder().
                        withCliente(cliente).
                        withPeluquero(peluquero).
                        withCorteMinInfo(peluquero.corteMin).
                        withUbicacionDelTurno(ubicacion).
                        build()

        // Si el peluquero ya posee 3 turnos (Pendiente o Confirmado), no se permite crear el turno.
        val limiteDeTurnosSimultaneos: Long = 3
        if(! turnoDAO.peluqueroPoseeCantidadDeTurnosPendientesOConfirmadosMayorOIgualA(peluquero, limiteDeTurnosSimultaneos)){
            turno.terminarEsperaTurno()
        }else {
            peluqueriaYaEmailSender.enviarMailAlClienteQueSuTurnoEstaEnEspera(turno)
        }

        // Si este turno es el turno #15 en espera, el peluquero no poseera mas turnos y se seteara ese estado
        val limiteDeTurnosEnEspera : Long = 5
        if(turnoDAO.peluqueroPoseeCantidadDeTurnosEnEsperaMayorOIgualA(peluquero,limiteDeTurnosEnEspera - 1)){
           peluquero.noPoseeMasTurnos()
        }

        serviciosSolicitadosInfo.forEach { sInfo -> sInfo.turno = turno }
        turno.serviciosSolicitadosInfo = serviciosSolicitadosInfo.toMutableList()

        return turnoDAO.save(turno)
    }

    @Transactional
    override fun confirmarTurno(turno: Turno): Turno {

        if(turno.peluquero.getEstaDeshabilitado() || turno.peluquero.getEstaDesconectado())
            throw PeluqueroDesconectado()

        turno.confirmar()

        if(! turno.getEstaConfirmado())
            throw TurnoNoSePuedeConfirmar()

        turno.peluquero.ocupar()

        val turnoUpdated = turnoDAO.update(turno)
        peluqueriaYaEmailSender.enviarMailDeConfirmacion(turno)
        return turnoUpdated
    }

    @Transactional
    override fun finalizarTurno(turno: Turno): Turno {
        turno.finalizar()

        if(! turno.getEstaFinalizado())
            throw TurnoNoSePuedeFinalizar()

        //Si el peluquero ya no posee turnos confirmados, su estado debe ser DISPONIBLE
        //O sino debe ponerse como OCUPADO porque ya posee por lo menos 1 turno en espera mas!
        if(! turnoDAO.peluqueroPoseeAlgunTurnoConfirmado(turno.peluquero)){
            turno.peluquero.desocupar()
        }else {
            turno.peluquero.poseeTurnos()
        }

        val turnoFinalizado = turnoDAO.update(turno)

        //Notificar al usuario que el turno ha finalizado
        peluqueriaYaEmailSender.enviarMailDeFinalizacionTurno(turnoFinalizado)

        //Buscar SI EXISTE el primer turno en espera (mas antiguo) y ponerlo directo en pendiente
        val turnoMasAntiguoEnEspera = turnoDAO.findTurnoEnEsperaMasAntiguoDelPeluquero(turno.peluquero)

        if(turnoMasAntiguoEnEspera.isPresent){
            turnoMasAntiguoEnEspera.get().terminarEsperaTurno()
            turnoDAO.update(turnoMasAntiguoEnEspera.get())

            //Mandar mail a ese turno del cliente diciendo que paso de espera a pendiente
            peluqueriaYaEmailSender.enviarMailAlClienteQueTerminoSuEspera(turnoMasAntiguoEnEspera.get())
        }

        return turnoFinalizado
    }

    @Transactional
    override fun cancelarTurno(turno: Turno) : Turno {
        turno.cancelar()

        if(! turno.getEstaCancelado())
            throw TurnoNoPuedeSerCancelado()

        turno.peluquero.poseeTurnos()

        val turnoCancelado = turnoDAO.update(turno)
        peluqueriaYaEmailSender.enviarMailAlClienteQueSeCanceloElTurno(turno)
        return turnoCancelado
    }

    override fun obtenerTodosLosTurnosDelPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno> {
        return turnoDAO.findAllConPeluquero(peluquero,pageable)
    }

    override fun obtenerTurnosHistoricosDelPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno> {
        return turnoDAO.findAllConPeluqueroYEstadoFinalizado(peluquero,pageable)
    }

    override fun obtenerTurnosPendientesOConfirmadosDelPeluquero(peluquero: Peluquero, pageable: Pageable): Page<Turno> {
        return turnoDAO.findAllConPeluqueroYEstadoPendientesOConfirmados(peluquero,pageable)
    }

    override fun puntuacionPromedioDelPeluquero(peluquero: Peluquero): Double {
        return turnoDAO.obtenerPromedioPuntuacionDeLosTurnosConPeluquero(peluquero)
    }

    override fun obtenerTurnosHistoricosDelCliente(cliente: Cliente, pageable: Pageable): Page<Turno> {
        return turnoDAO.findAllConClienteYEstadoFinalizadoOCancelado(cliente,pageable)
    }

    override fun obtenerTurnosEnEsperaOPendientesOConfirmadosDelCliente(cliente: Cliente, pageable: Pageable): Page<Turno> {
        return turnoDAO.findAllConClienteYEstadoEnEsperaOPendienteOConfirmado(cliente, pageable)
    }

    @Transactional
    override fun calificarTurno(turno: Turno, puntaje: Int): Turno {
        turno.puntuar(puntaje)
        return turnoDAO.update(turno)
    }

    private fun distanciaEnRangoDelPeluqueroEstaExcedida(ubicacion: Ubicacion, peluquero:Peluquero):Boolean {
        //Por defecto la ubicacion cercana
        var distanciaToleranciaPeluquero = peluquero.distanciaMax

        //Si no posee distanciaMax, es decir es 0. Se pone la distancia por defecto de la ubicacion cercana utilizada en la query
        if(distanciaToleranciaPeluquero == BigDecimal.ZERO){
            distanciaToleranciaPeluquero = BigDecimal(5.3)
        }

        //Le agrego el delta 0.2 por el geoDistanceServiceAPI
        distanciaToleranciaPeluquero = distanciaToleranciaPeluquero.plus(BigDecimal(0.2))

        return geoDistanceServiceApi.distanciaUbicacionesEnKM(peluquero.ubicacion,ubicacion) > distanciaToleranciaPeluquero.toDouble()
    }
}