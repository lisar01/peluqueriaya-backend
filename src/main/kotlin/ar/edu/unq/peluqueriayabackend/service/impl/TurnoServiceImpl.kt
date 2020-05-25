package ar.edu.unq.peluqueriayabackend.service.impl

import ar.edu.unq.peluqueriayabackend.exception.*
import ar.edu.unq.peluqueriayabackend.model.*
import ar.edu.unq.peluqueriayabackend.persistence.PeluqueroDAO
import ar.edu.unq.peluqueriayabackend.persistence.TurnoDAO
import ar.edu.unq.peluqueriayabackend.service.TurnoService
import ar.edu.unq.peluqueriayabackend.service.emailSender.impl.PeluqueriaYaEmailSender
import ar.edu.unq.peluqueriayabackend.service.geodistance.GeoDistanceServiceApi
import org.springframework.beans.factory.annotation.Autowired
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

        if(peluquero.estaDesconectado() || peluquero.estaDeshabilitado())
            throw PeluqueroDesconectado()

        if(! peluquero.tieneTurnosEnEspera())
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

        if(turno.peluquero.estaDeshabilitado() || turno.peluquero.estaDesconectado())
            throw PeluqueroDesconectado()

        turno.confirmar()

        if(! turno.estaConfirmado())
            throw TurnoNoSePuedeConfirmar()

        turno.peluquero.ocupar()

        val turnoUpdated = turnoDAO.update(turno)
        peluqueriaYaEmailSender.enviarMailDeConfirmacion(turno)
        return turnoUpdated
    }

    @Transactional
    override fun finalizarTurno(turno: Turno): Turno {
        turno.finalizar()

        if(! turno.estaFinalizado())
            throw TurnoNoSePuedeFinalizar()

        //Si el peluquero ya no posee turnos confirmados, su estado debe ser DISPONIBLE
        //O sino debe ponerse como OCUPADO porque ya posee por lo menos 1 turno en espera mas!
        if(! turnoDAO.peluqueroPoseeAlgunTurnoConfirmado(turno.peluquero)){
            turno.peluquero.desocupar()
        }else {
            turno.peluquero.poseeTurnos()
        }

        val turnoFinalizado = turnoDAO.update(turno)

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

        if(! turno.estaCancelado())
            throw TurnoNoPuedeSerCancelado()

        turno.peluquero.poseeTurnos()

        val turnoCancelado = turnoDAO.update(turno)
        peluqueriaYaEmailSender.enviarMailAlClienteQueSeCanceloElTurno(turno)
        return turnoCancelado
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