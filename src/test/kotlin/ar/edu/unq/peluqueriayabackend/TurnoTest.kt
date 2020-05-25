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
    fun testUnTurnoConCorteMinInfo150YConServicio1ConPrecio250YServicio2ConPrecio550ElPrecioTotalDeEseTurnoEs950(){

        val serviciosSolicitadosInfo = mutableListOf<ServicioInfo>()
        val servicio1 = ServicioInfo.Builder().withPrecio(BigDecimal(250)).build()
        val servicio2 = ServicioInfo.Builder().withPrecio(BigDecimal(550)).build()

        serviciosSolicitadosInfo.add(servicio1)
        serviciosSolicitadosInfo.add(servicio2)

        val turno1 = Turno.Builder().withServiciosSolicitadosInfo(serviciosSolicitadosInfo).
                                        withCorteMinInfo(BigDecimal(150)).build()

        assertEquals(turno1.precioTotal(),BigDecimal(950))
    }

    @Test
    fun testUnTurnoConCorteMinInfo150YConServicio1ConPrecio250YServicio2ConPrecio550YServicio3ConPrecio50Punto50ElPrecioTotalDeEseTurnoEs1000Punto50(){

        val servicio1 = ServicioInfo.Builder().withPrecio(BigDecimal(250)).build()
        val servicio2 = ServicioInfo.Builder().withPrecio(BigDecimal(550)).build()
        val servicio3 = ServicioInfo.Builder().withPrecio(BigDecimal(50.50)).build()
        val serviciosSolicitados = mutableListOf<ServicioInfo>()

        serviciosSolicitados.add(servicio1)
        serviciosSolicitados.add(servicio2)
        serviciosSolicitados.add(servicio3)

        val turno1 = Turno.Builder().withServiciosSolicitadosInfo(serviciosSolicitados).
                                    withCorteMinInfo(BigDecimal(150)).build()

        assertEquals(turno1.precioTotal(),BigDecimal(1000.50))
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
        val turno5 = Turno.Builder().withEstado(TurnoState.ESPERANDO).build()
        val turno6 = Turno.Builder().withEstado(TurnoState.CANCELADO).build()

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
        assertThrows<ValorDePuntuacionErroneo> {
            turno5.puntuar(7)
        }
        assertThrows<ValorDePuntuacionErroneo> {
            turno6.puntuar(8)
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
    fun testUnTurnoConEstadoEsperandoAlPuntuarloNoHaceNadaYPorLoTantoPoseeComoPuntaje0(){
        val turno = Turno.Builder().withEstado(TurnoState.ESPERANDO).build()

        turno.puntuar(3)
        assertEquals(turno.puntaje,0)
    }

    @Test
    fun testUnTurnoConEstadoCanceladoAlPuntuarloNoHaceNadaYPorLoTantoPoseeComoPuntaje0(){
        val turno = Turno.Builder().withEstado(TurnoState.CANCELADO).build()

        turno.puntuar(3)
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
    fun testUnTurnoPendienteOCanceladoOCOnfirmadoOFinalizadoAlQuererTerminarEsperaTurnoNoHaceNadaYDejaLasFechasCorrespondientes(){
        val fechaInicio = LocalDateTime.now().minusHours(3)
        val fechaPendiente = LocalDateTime.now().minusHours(2)
        val fechaCancelado = LocalDateTime.now().minusHours(2)
        val fechaConfirmacion = LocalDateTime.now().minusHours(1)
        val fechaFin = LocalDateTime.now().minusMinutes(10)

        val turno1 = Turno.Builder().withEstado(TurnoState.PENDIENTE)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .build()

        val turno2 = Turno.Builder().withEstado(TurnoState.CANCELADO)
                .withFechaInicio(fechaInicio)
                .withFechaCancelacion(fechaCancelado)
                .build()

        val turno3 = Turno.Builder().withEstado(TurnoState.CONFIRMADO)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .withFechaConfirmacion(fechaConfirmacion)
                .build()

        val turno4 = Turno.Builder().withEstado(TurnoState.FINALIZADO)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .withFechaConfirmacion(fechaConfirmacion)
                .withFechaFin(fechaFin)
                .build()

        turno1.terminarEsperaTurno()
        turno2.terminarEsperaTurno()
        turno3.terminarEsperaTurno()
        turno4.terminarEsperaTurno()

        assertTrue(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaFinalizado())

        assertEquals(turno1.fechaInicio,fechaInicio)
        assertEquals(turno1.fechaPendiente,fechaPendiente)
        assertNull(turno1.fechaCancelacion)
        assertNull(turno1.fechaConfirmacion)
        assertNull(turno1.fechaFin)


        assertTrue(turno2.getEstaCancelado())
        assertFalse(turno2.getEstaPendiente())
        assertFalse(turno2.getEstaConfirmado())
        assertFalse(turno2.getEstaEsperando())
        assertFalse(turno2.getEstaFinalizado())

        assertEquals(turno2.fechaInicio,fechaInicio)
        assertEquals(turno2.fechaCancelacion,fechaCancelado)
        assertNull(turno2.fechaPendiente)
        assertNull(turno2.fechaConfirmacion)
        assertNull(turno2.fechaFin)


        assertTrue(turno3.getEstaConfirmado())
        assertFalse(turno3.getEstaCancelado())
        assertFalse(turno3.getEstaPendiente())
        assertFalse(turno3.getEstaEsperando())
        assertFalse(turno3.getEstaFinalizado())

        assertEquals(turno3.fechaInicio,fechaInicio)
        assertEquals(turno3.fechaPendiente,fechaPendiente)
        assertEquals(turno3.fechaConfirmacion,fechaConfirmacion)
        assertNull(turno3.fechaCancelacion)
        assertNull(turno3.fechaFin)


        assertTrue(turno4.getEstaFinalizado())
        assertFalse(turno4.getEstaCancelado())
        assertFalse(turno4.getEstaConfirmado())
        assertFalse(turno4.getEstaEsperando())
        assertFalse(turno4.getEstaPendiente())

        assertEquals(turno4.fechaInicio,fechaInicio)
        assertEquals(turno4.fechaPendiente,fechaPendiente)
        assertEquals(turno4.fechaConfirmacion,fechaConfirmacion)
        assertEquals(turno4.fechaFin, fechaFin)
        assertNull(turno4.fechaCancelacion)
    }

    @Test
    fun testUnTurnoEsperandoAlTerminarEsperaTurnoCambiaSuEstadoAPendienteYConElloSuFechaDePendiente(){
        val fechaInicio = LocalDateTime.now().minusHours(1)
        val turno1 = Turno.Builder().
                            withEstado(TurnoState.ESPERANDO).
                            withFechaInicio(fechaInicio).
                            build()

        turno1.terminarEsperaTurno()

        assertTrue(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaFinalizado())

        assertEquals(turno1.fechaInicio, fechaInicio)
        assertNull(turno1.fechaCancelacion)
        assertNull(turno1.fechaConfirmacion)
        assertNull(turno1.fechaFin)
        //TODO
        // Calcular el delta de alguna forma con la fecha actual
        assertTrue(turno1.fechaPendiente!!.isAfter(fechaInicio))

    }

    @Test
    fun testUnTurnoCanceladoOConfirmadoOFinalizadoAlCancelarloNoSucedeNadaYDejaSusFechaCorrespondientes(){
        val fechaInicio = LocalDateTime.now().minusHours(3)
        val fechaPendiente = LocalDateTime.now().minusHours(2)
        val fechaCancelado = LocalDateTime.now().minusHours(2)
        val fechaConfirmacion = LocalDateTime.now().minusHours(1)
        val fechaFin = LocalDateTime.now().minusMinutes(10)


        val turno1 = Turno.Builder().withEstado(TurnoState.CANCELADO)
                .withFechaInicio(fechaInicio)
                .withFechaCancelacion(fechaCancelado)
                .build()

        val turno2 = Turno.Builder().withEstado(TurnoState.CONFIRMADO)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .withFechaConfirmacion(fechaConfirmacion)
                .build()

        val turno3 = Turno.Builder().withEstado(TurnoState.FINALIZADO)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .withFechaConfirmacion(fechaConfirmacion)
                .withFechaFin(fechaFin)
                .build()

        turno1.cancelar()
        turno2.cancelar()
        turno3.cancelar()

        assertTrue(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaFinalizado())

        assertEquals(turno1.fechaInicio,fechaInicio)
        assertEquals(turno1.fechaCancelacion,fechaCancelado)
        assertNull(turno1.fechaPendiente)
        assertNull(turno1.fechaConfirmacion)
        assertNull(turno1.fechaFin)


        assertTrue(turno2.getEstaConfirmado())
        assertFalse(turno2.getEstaPendiente())
        assertFalse(turno2.getEstaCancelado())
        assertFalse(turno2.getEstaEsperando())
        assertFalse(turno2.getEstaFinalizado())

        assertEquals(turno2.fechaInicio,fechaInicio)
        assertEquals(turno3.fechaPendiente,fechaPendiente)
        assertEquals(turno3.fechaConfirmacion,fechaConfirmacion)
        assertNull(turno2.fechaCancelacion)
        assertNull(turno2.fechaFin)


        assertTrue(turno3.getEstaFinalizado())
        assertFalse(turno3.getEstaCancelado())
        assertFalse(turno3.getEstaPendiente())
        assertFalse(turno3.getEstaEsperando())
        assertFalse(turno3.getEstaConfirmado())

        assertEquals(turno3.fechaInicio,fechaInicio)
        assertEquals(turno3.fechaPendiente,fechaPendiente)
        assertEquals(turno3.fechaConfirmacion,fechaConfirmacion)
        assertEquals(turno3.fechaFin,fechaFin)
        assertNull(turno3.fechaCancelacion)
    }

    @Test
    fun testUnTurnoPendienteOEsperandoAlCancelarloCambiaSuEstadoACanceladoYSeteaSuFechaDeCancelacionALaFechaActual(){
        val fechaInicio = LocalDateTime.now().minusHours(1)
        val fechaPendiente = LocalDateTime.now().minusMinutes(10)
        val turno1 = Turno.Builder().withEstado(TurnoState.PENDIENTE)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .build()

        val turno2 = Turno.Builder().withEstado(TurnoState.ESPERANDO).
                        withFechaInicio(fechaInicio).
                        build()

        turno1.cancelar()
        turno2.cancelar()

        assertTrue(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaFinalizado())

        assertEquals(turno1.fechaInicio, fechaInicio)
        assertEquals(turno1.fechaPendiente, fechaPendiente)
        assertNull(turno1.fechaConfirmacion)
        assertNull(turno1.fechaFin)
        //TODO
        // Calcular el delta de alguna forma con la fecha actual
        assertTrue(turno1.fechaCancelacion!!.isAfter(fechaInicio))
        assertTrue(turno1.fechaCancelacion!!.isAfter(fechaPendiente))


        assertTrue(turno2.getEstaCancelado())
        assertFalse(turno2.getEstaPendiente())
        assertFalse(turno2.getEstaConfirmado())
        assertFalse(turno2.getEstaEsperando())
        assertFalse(turno2.getEstaFinalizado())

        assertEquals(turno2.fechaInicio, fechaInicio)
        assertNull(turno2.fechaPendiente)
        assertNull(turno2.fechaConfirmacion)
        assertNull(turno2.fechaFin)
        //TODO
        // Calcular el delta de alguna forma con la fecha actual
        assertTrue(turno2.fechaCancelacion!!.isAfter(fechaInicio))
    }

    @Test
    fun testUnTurnoPendienteAlConfirmarloElTurnoCambiaSuEstadoAConfirmadoYSeSeteaComoFechaConfirmacionLaActual(){
        val fechaInicio = LocalDateTime.now().minusHours(1)
        val fechaPendiente = LocalDateTime.now().minusMinutes(10)
        val turno1 = Turno.Builder().withEstado(TurnoState.PENDIENTE)
                .withFechaInicio(fechaInicio)
                .withFechaPendiente(fechaPendiente)
                .build()

        assertTrue(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaFinalizado())

        turno1.confirmar()

        assertTrue(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaFinalizado())

        assertEquals(turno1.fechaInicio, fechaInicio)
        assertEquals(turno1.fechaPendiente, fechaPendiente)
        //TODO
        // Calcular el delta de alguna forma con la fecha actual
        assertTrue(turno1.fechaConfirmacion!!.isAfter(fechaInicio))
        assertTrue(turno1.fechaConfirmacion!!.isAfter(fechaPendiente))
    }

    @Test
    fun testUnTurnoCanceladoOEsperandoOConfirmadoOFinalizadoAlQuererConfirmarloElTurnoNoHaceNadaYCadaUnoSigueConSusEstadosYFechasCorrespondientes(){
        val fechaInicio = LocalDateTime.now().minusHours(2)
        var fechaPendiente = LocalDateTime.now().minusHours(1)
        val fechaCancelacion =  LocalDateTime.now().minusHours(1)
        val fechaConfirmacion = LocalDateTime.now().minusMinutes(20)
        val fechaFin = LocalDateTime.now()

        val turno1 = Turno.Builder().withEstado(TurnoState.CONFIRMADO)
                                    .withFechaInicio(fechaInicio)
                                    .withFechaPendiente(fechaPendiente)
                                    .withFechaConfirmacion(fechaConfirmacion)
                                    .build()

        val turno2 = Turno.Builder().withEstado(TurnoState.FINALIZADO).
                                    withFechaInicio(fechaInicio).
                                    withFechaPendiente(fechaPendiente).
                                    withFechaConfirmacion(fechaConfirmacion).
                                    withFechaFin(fechaFin).
                                    build()

        val turno3 = Turno.Builder().withEstado(TurnoState.CANCELADO).
                                    withFechaInicio(fechaInicio).
                                    withFechaPendiente(fechaPendiente).
                                    withFechaCancelacion(fechaCancelacion).
                                    build()

        val turno4 = Turno.Builder().withEstado(TurnoState.ESPERANDO).
                                    withFechaInicio(fechaInicio).
                                    build()

        turno1.confirmar()
        turno2.confirmar()
        turno3.confirmar()
        turno4.confirmar()

        assertTrue(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaEsperando())
        assertFalse(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaFinalizado())

        assertEquals(turno1.fechaInicio,fechaInicio)
        assertEquals(turno1.fechaPendiente,fechaPendiente)
        assertEquals(turno1.fechaConfirmacion,fechaConfirmacion)
        assertNull(turno1.fechaFin)

        assertTrue(turno2.getEstaFinalizado())
        assertFalse(turno2.getEstaCancelado())
        assertFalse(turno2.getEstaEsperando())
        assertFalse(turno2.getEstaPendiente())
        assertFalse(turno2.getEstaConfirmado())
        assertEquals(turno2.fechaInicio,fechaInicio)
        assertEquals(turno2.fechaPendiente,fechaPendiente)
        assertEquals(turno2.fechaConfirmacion,fechaConfirmacion)
        assertEquals(turno2.fechaFin,fechaFin)

        assertTrue(turno3.getEstaCancelado())
        assertFalse(turno3.getEstaFinalizado())
        assertFalse(turno3.getEstaEsperando())
        assertFalse(turno3.getEstaPendiente())
        assertFalse(turno3.getEstaConfirmado())
        assertEquals(turno3.fechaInicio,fechaInicio)
        assertEquals(turno3.fechaCancelacion,fechaCancelacion)
        assertNull(turno3.fechaConfirmacion)
        assertNull(turno3.fechaFin)

        assertTrue(turno4.getEstaEsperando())
        assertFalse(turno4.getEstaCancelado())
        assertFalse(turno4.getEstaFinalizado())
        assertFalse(turno4.getEstaPendiente())
        assertFalse(turno4.getEstaConfirmado())
        assertEquals(turno4.fechaInicio,fechaInicio)
        assertNull(turno4.fechaConfirmacion)
    }

    @Test
    fun testUnTurnoEsperandoOCanceladoOPendienteOFinalizadoAlQuererFinalizarElTurnoNoHaceNadaYNoPoseeFechaFinEnPendiente() {
        val fechaInicio = LocalDateTime.now().minusHours(2)
        val fechaPendiente = LocalDateTime.now().minusHours(1)
        val fechaCancelado = LocalDateTime.now().minusMinutes(3)
        val turno2FechaConfirmacion = LocalDateTime.now().minusMinutes(10)
        val turno2FechaFin = LocalDateTime.now()


        val turno1 = Turno.Builder().
                            withEstado(TurnoState.PENDIENTE).
                            withFechaInicio(fechaInicio).
                            withFechaPendiente(fechaPendiente)
                            .build()

        val turno2 = Turno.Builder().withEstado(TurnoState.FINALIZADO).
                                    withFechaInicio(fechaInicio).
                                    withFechaPendiente(fechaPendiente).
                                    withFechaConfirmacion(turno2FechaConfirmacion).
                                    withFechaFin(turno2FechaFin).
                                    build()

        val turno3 = Turno.Builder().
                            withEstado(TurnoState.CANCELADO).
                            withFechaInicio(fechaInicio).
                            withFechaCancelacion(fechaCancelado).
                            build()

        val turno4 = Turno.Builder().
                        withEstado(TurnoState.ESPERANDO).
                        withFechaInicio(fechaInicio).
                        build()

        turno1.finalizar()
        turno2.finalizar()
        turno3.finalizar()
        turno4.finalizar()

        assertTrue(turno1.getEstaPendiente())
        assertFalse(turno1.getEstaCancelado())
        assertFalse(turno1.getEstaFinalizado())
        assertFalse(turno1.getEstaConfirmado())
        assertFalse(turno1.getEstaEsperando())
        assertEquals(turno1.fechaInicio,fechaInicio)
        assertEquals(turno1.fechaPendiente,fechaPendiente)
        assertNull(turno1.fechaConfirmacion)
        assertNull(turno1.fechaFin)
        assertNull(turno1.fechaCancelacion)

        assertTrue(turno2.getEstaFinalizado())
        assertFalse(turno2.getEstaCancelado())
        assertFalse(turno2.getEstaPendiente())
        assertFalse(turno2.getEstaConfirmado())
        assertFalse(turno2.getEstaEsperando())
        assertEquals(turno2.fechaInicio,fechaInicio)
        assertEquals(turno2.fechaPendiente,fechaPendiente)
        assertEquals(turno2.fechaConfirmacion,turno2FechaConfirmacion)
        assertEquals(turno2.fechaFin,turno2FechaFin)
        assertNull(turno2.fechaCancelacion)

        assertTrue(turno3.getEstaCancelado())
        assertFalse(turno3.getEstaPendiente())
        assertFalse(turno3.getEstaFinalizado())
        assertFalse(turno3.getEstaConfirmado())
        assertFalse(turno3.getEstaEsperando())
        assertEquals(turno3.fechaInicio,fechaInicio)
        assertEquals(turno3.fechaCancelacion,fechaCancelado)
        assertNull(turno3.fechaPendiente)
        assertNull(turno3.fechaConfirmacion)
        assertNull(turno3.fechaFin)

        assertTrue(turno4.getEstaEsperando())
        assertFalse(turno4.getEstaCancelado())
        assertFalse(turno4.getEstaFinalizado())
        assertFalse(turno4.getEstaConfirmado())
        assertFalse(turno4.getEstaPendiente())
        assertEquals(turno4.fechaInicio,fechaInicio)
        assertNull(turno4.fechaPendiente)
        assertNull(turno4.fechaConfirmacion)
        assertNull(turno4.fechaFin)
        assertNull(turno4.fechaCancelacion)
    }

    @Test
    fun testUnTurnoConfirmadoAlFinalizarTurnoSeSeteaElEstadoEnFinalizadoYConLaFechaActualEnFechaFin() {
        val turno = Turno.Builder().withEstado(TurnoState.CONFIRMADO).
                                    withFechaInicio(LocalDateTime.now().minusHours(2)).
                                    withFechaConfirmacion(LocalDateTime.now().minusHours(1)).
                                    build()

        turno.finalizar()

        assertEquals(turno.estado,TurnoState.FINALIZADO)

        //TODO
        // BUSCAR COMO HACER UN ASSERT CON DELTA DE FECHA ACTUAL
        assertNotNull(turno.fechaFin)
        assertTrue(turno.fechaFin!!.isAfter(turno.fechaInicio))
        assertTrue(turno.fechaFin!!.isAfter(turno.fechaConfirmacion))
    }

}