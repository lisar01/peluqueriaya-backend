package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.exception.ValorDePuntuacionErroneo
import ar.edu.unq.peluqueriayabackend.model.ServicioInfo
import ar.edu.unq.peluqueriayabackend.model.Turno
import ar.edu.unq.peluqueriayabackend.model.TurnoState
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime

class TurnoTest {

    @Test
    fun testUnTurnoSinServiciosPedidosSuPrecioTotalEsElPrecioCorteMinInfoEnEsteCasoEs150(){
        val serviciosPedidos = mutableListOf<ServicioInfo>()
        val turno1 = Turno.Builder().withServiciosSolicitadosInfo(serviciosPedidos).
                                    withCorteMinInfo(BigDecimal(150)).build()

        assertEquals(turno1.precioTotal(),BigDecimal(150))
    }

    @Test
    fun testUnTurnoConServicio1ConPrecio250YServicio2ConPrecio550ElPrecioTotalDeEseTurnoEs800(){


        val serviciosSolicitadosInfo = mutableListOf<ServicioInfo>()
        val servicio1 = ServicioInfo.Builder().withPrecio(BigDecimal(250)).build()
        val servicio2 = ServicioInfo.Builder().withPrecio(BigDecimal(550)).build()

        serviciosSolicitadosInfo.add(servicio1)
        serviciosSolicitadosInfo.add(servicio2)

        val turno1 = Turno.Builder().withServiciosSolicitadosInfo(serviciosSolicitadosInfo).build()

        assertEquals(turno1.precioTotal(),BigDecimal(800))
    }

    @Test
    fun testUnTurnoConServicio1ConPrecio250YServicio2ConPrecio550YServicio3ConPrecio50Punto50ElPrecioTotalDeEseTurnoEs850Punto50(){

        val serviciosSolicitadosInfo = mutableListOf<ServicioInfo>()
        val servicio1 = ServicioInfo.Builder().withPrecio(BigDecimal(250)).build()
        val servicio2 = ServicioInfo.Builder().withPrecio(BigDecimal(550)).build()
        val servicio3 = ServicioInfo.Builder().withPrecio(BigDecimal(50.50)).build()
        val serviciosSolicitados = mutableListOf<ServicioInfo>()

        serviciosSolicitados.add(servicio1)
        serviciosSolicitados.add(servicio2)
        serviciosSolicitados.add(servicio3)

        val turno1 = Turno.Builder().withServiciosSolicitadosInfo(serviciosSolicitados).build()

        assertEquals(turno1.precioTotal(),BigDecimal(850.50))
    }

    @Test
    fun testUnTurnoSinPuntuarPoseeComoPuntaje0(){
        val turno = Turno.Builder().build()

        assertEquals(turno.puntaje,0)
    }

    @Test
    fun testUnTurnoCualquieraAlPuntuarloConUnValorQueNoEsteEnElRangoEntre1Y5LanzaExcepcionValorDePuntuacionErroneo(){
        val turno1 = Turno.Builder().build()
        val turno2 = Turno.Builder().withEstado(TurnoState.FINALIZADO).build()
        val turno3 = Turno.Builder().withEstado(TurnoState.PENDIENTE).build()
        val turno4 = Turno.Builder().withEstado(TurnoState.CONFIRMADO).build()

        assertThrows<ValorDePuntuacionErroneo> {
            turno1.puntuar(0)
        }
        assertThrows<ValorDePuntuacionErroneo> {
            turno2.puntuar(-1)
        }
        assertThrows<ValorDePuntuacionErroneo> {
            turno3.puntuar(6)
        }
        assertThrows<ValorDePuntuacionErroneo> {
            turno4.puntuar(10)
        }
    }

    @Test
    fun testUnTurnoConEstadoPendienteAlPuntuarloNoHaceNadaYPorLoTantoPoseeComoPuntaje0(){
        val turno = Turno.Builder().withEstado(TurnoState.PENDIENTE).build()

        turno.puntuar(3)
        assertEquals(turno.puntaje,0)
    }

    @Test
    fun testUnTurnoConEstadoConfirmadoAlPuntuarloNoHaceNadaYPorLoTantoPoseeComoPuntaje0(){
        val turno = Turno.Builder().withEstado(TurnoState.CONFIRMADO).build()

        turno.puntuar(5)
        assertEquals(turno.puntaje,0)
    }

    @Test
    fun testUnTurnoConEstadoFinalizadoAlPuntuarloCon1DejaAlTurnoConPuntaje1(){
        val turno = Turno.Builder().withEstado(TurnoState.FINALIZADO).build()

        turno.puntuar(1)
        assertEquals(turno.puntaje,1)
    }

    @Test
    fun testTurno1ConEstadoFinalizadoAlPuntuarloCon5DejaAlTurno1ConPuntaje5YLoMismoConElTurno2ConElPuntaje2(){
        val turno1 = Turno.Builder().withEstado(TurnoState.FINALIZADO).build()
        val turno2 = Turno.Builder().withEstado(TurnoState.FINALIZADO).build()

        turno1.puntuar(5)
        turno2.puntuar(2)

        assertEquals(turno1.puntaje,5)
        assertEquals(turno2.puntaje,2)
    }

    @Test
    fun testUnTurnoPendienteAlConfirmarloElTurnoCambiaSuEstadoAConfirmadoYSeSeteaComoFechaConfirmacionLaActual(){
        val fechaInicio = LocalDateTime.now().minusHours(1)
        val turno1 = Turno.Builder().withEstado(TurnoState.PENDIENTE)
                .withFechaInicio(fechaInicio)
                .build()

        turno1.confirmarTurno()

        assertEquals(turno1.estado,TurnoState.CONFIRMADO)
        assertEquals(turno1.fechaInicio,fechaInicio)
        //TODO
        // Calcular el delta de alguna forma con la fecha actual
        assertTrue(turno1.fechaConfirmacion!!.isAfter(fechaInicio))
    }

    @Test
    fun testUnTurnoConfirmadoOFinalizadoAlQuererConfirmarloElTurnoNoHaceNadaYCadaUnoSigueConSusEstadosYFechasCorrespondientes(){
        val fechaInicio = LocalDateTime.now().minusHours(2)
        val fechaConfirmacion = LocalDateTime.now().minusHours(1)
        val fechaFin = LocalDateTime.now()

        val turno1 = Turno.Builder().withEstado(TurnoState.CONFIRMADO)
                                    .withFechaInicio(fechaInicio)
                                    .withFechaConfirmacion(fechaConfirmacion)
                                    .build()

        val turno2 = Turno.Builder().withEstado(TurnoState.FINALIZADO).
                                    withFechaInicio(fechaInicio).
                                    withFechaConfirmacion(fechaConfirmacion).
                                    withFechaFin(fechaFin).
                                    build()

        turno1.confirmarTurno()
        turno2.confirmarTurno()

        assertEquals(turno1.estado,TurnoState.CONFIRMADO)
        assertEquals(turno1.fechaInicio,fechaInicio)
        assertEquals(turno1.fechaConfirmacion,fechaConfirmacion)
        assertNull(turno1.fechaFin)

        assertEquals(turno2.estado,TurnoState.FINALIZADO)
        assertEquals(turno2.fechaInicio,fechaInicio)
        assertEquals(turno2.fechaConfirmacion,fechaConfirmacion)
        assertEquals(turno2.fechaFin,fechaFin)
    }

    @Test
    fun testUnTurnoPendienteOFinalizadoAlQuererFinalizarElTurnoNoHaceNadaYNoPoseeFechaFinEnPendiente() {
        val turno2FechaConfirmacion = LocalDateTime.now().minusHours(1)
        val turno2FechaFin = LocalDateTime.now()

        val turno1 = Turno.Builder().withEstado(TurnoState.PENDIENTE).build()
        val turno2 = Turno.Builder().withEstado(TurnoState.FINALIZADO).
                                    withFechaConfirmacion(turno2FechaConfirmacion).
                                    withFechaFin(turno2FechaFin).
                                    build()

        turno1.finalizarTurno()
        turno2.finalizarTurno()

        assertEquals(turno1.estado,TurnoState.PENDIENTE)
        assertNull(turno1.fechaConfirmacion)
        assertNull(turno1.fechaFin)

        assertEquals(turno2.estado,TurnoState.FINALIZADO)
        assertEquals(turno2.fechaConfirmacion,turno2FechaConfirmacion)
        assertEquals(turno2.fechaFin,turno2FechaFin)
    }

    @Test
    fun testUnTurnoConfirmadoAlFinalizarTurnoSeSeteaElEstadoEnFinalizadoYConLaFechaActualEnFechaFin() {
        val turno = Turno.Builder().withEstado(TurnoState.CONFIRMADO).
                                    withFechaInicio(LocalDateTime.now().minusHours(2)).
                                    withFechaConfirmacion(LocalDateTime.now().minusHours(1)).
                                    build()

        turno.finalizarTurno()

        assertEquals(turno.estado,TurnoState.FINALIZADO)

        //TODO
        // BUSCAR COMO HACER UN ASSERT CON DELTA DE FECHA ACTUAL
        assertNotNull(turno.fechaFin)
        assertTrue(turno.fechaFin!!.isAfter(turno.fechaInicio))
        assertTrue(turno.fechaFin!!.isAfter(turno.fechaConfirmacion))
    }

}