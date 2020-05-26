package ar.edu.unq.peluqueriayabackend

import ar.edu.unq.peluqueriayabackend.model.Peluquero
import ar.edu.unq.peluqueriayabackend.model.PeluqueroState
import ar.edu.unq.peluqueriayabackend.model.PeluqueroType
import ar.edu.unq.peluqueriayabackend.model.Servicio
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class PeluqueroTest {

    @Test
    fun testUnPeluqueroNuevoPoseeListaVaciaEnServiciosYTipos() {
        val peluquero1 = Peluquero.Builder().build()

        assertTrue(peluquero1.servicios.isEmpty())
        assertTrue(peluquero1.tipos.isEmpty())
    }

    @Test
    fun testPeluquero1SinServiciosCuandoSeLeAgreganServicio1YServicio2AhoraPoseeEsosServiciosEnSuListaDeServiciosEnCambioElPelquero2NoPoseeNingunServicio() {
        val peluquero1 = Peluquero.Builder().build()
        val peluquero2 = Peluquero.Builder().build()

        val servicio1 = Servicio.Builder().build()
        val servicio2 = Servicio.Builder().build()

        peluquero1.agregarServicio(servicio1)
        peluquero1.agregarServicio(servicio2)

        assertFalse(peluquero1.servicios.isEmpty())
        assertEquals(peluquero1.servicios.size,2)
        assertTrue(peluquero1.servicios.contains(servicio1))
        assertTrue(peluquero1.servicios.contains(servicio2))

        assertTrue(peluquero2.servicios.isEmpty())
    }

    @Test
    fun testELPeluquero1Y2PoseenLosMismoServiciosPeroElPeluquero1EliminaElServicio1YPoseeLosDemasEnCambioElPeluquero2EliminaElServicio2YPoseeLosDemas() {
        val serviciosP1 = mutableListOf<Servicio>()
        val serviciosP2 = mutableListOf<Servicio>()

        val servicio1 = Servicio.Builder().build()
        val servicio2 = Servicio.Builder().build()
        val servicio3 = Servicio.Builder().build()
        serviciosP1.add(servicio1)
        serviciosP1.add(servicio2)
        serviciosP1.add(servicio3)

        serviciosP2.add(servicio1)
        serviciosP2.add(servicio2)
        serviciosP2.add(servicio3)

        val peluquero1 = Peluquero.Builder().withServicios(serviciosP1).build()
        val peluquero2 = Peluquero.Builder().withServicios(serviciosP2).build()

        peluquero1.eliminarServicio(servicio1)

        peluquero2.eliminarServicio(servicio2)

        assertEquals(peluquero1.servicios.size,2)
        assertEquals(peluquero2.servicios.size,2)

        assertFalse(peluquero1.servicios.contains(servicio1))
        assertTrue(peluquero1.servicios.contains(servicio2))
        assertTrue(peluquero1.servicios.contains(servicio3))

        assertFalse(peluquero2.servicios.contains(servicio2))
        assertTrue(peluquero2.servicios.contains(servicio1))
        assertTrue(peluquero2.servicios.contains(servicio3))

    }

    @Test
    fun testPeluquero1SinTiposCuandoSeLeAgreganElTipoKIDSYHOMBRESAhoraPoseeEsosTiposEnSuSetDeTiposEnCambioElPelquero2NoPoseeNingunTipo() {
        val peluquero1 = Peluquero.Builder().build()
        val peluquero2 = Peluquero.Builder().build()

        peluquero1.agregarTipo(PeluqueroType.KIDS)
        peluquero1.agregarTipo(PeluqueroType.HOMBRE)

        assertFalse(peluquero1.tipos.isEmpty())
        assertEquals(peluquero1.tipos.size,2)
        assertTrue(peluquero1.tipos.contains(PeluqueroType.KIDS))
        assertTrue(peluquero1.tipos.contains(PeluqueroType.HOMBRE))

        assertTrue(peluquero2.tipos.isEmpty())
    }

    @Test
    fun testPeluquero1ConTiposKIDS_Y_HOMBRE_CuandoSeLeAgregaUnTipoQueYaPoseeNoLoAgrega() {
        val tipos = mutableSetOf<PeluqueroType>()
        tipos.add(PeluqueroType.HOMBRE)
        tipos.add(PeluqueroType.KIDS)

        val peluquero1 = Peluquero.Builder().withTipos(tipos).build()

        peluquero1.agregarTipo(PeluqueroType.KIDS)

        assertFalse(peluquero1.tipos.isEmpty())
        assertEquals(peluquero1.tipos.size,2)
        assertTrue(peluquero1.tipos.contains(PeluqueroType.KIDS))
        assertTrue(peluquero1.tipos.contains(PeluqueroType.HOMBRE))

        peluquero1.agregarTipo(PeluqueroType.HOMBRE)

        assertFalse(peluquero1.tipos.isEmpty())
        assertEquals(peluquero1.tipos.size,2)
        assertTrue(peluquero1.tipos.contains(PeluqueroType.KIDS))
        assertTrue(peluquero1.tipos.contains(PeluqueroType.HOMBRE))

    }

    @Test
    fun testELPeluquero1Y2PoseenTodosLosTiposPeroElPeluquero1EliminaElTipoKIDSYPoseeLosDemasEnCambioElPeluquero2EliminaElTipoHOMBREYPoseeLosDemas() {
        val tiposP1 = mutableSetOf<PeluqueroType>()
        val tiposP2 = mutableSetOf<PeluqueroType>()

        tiposP1.add(PeluqueroType.MUJER)
        tiposP1.add(PeluqueroType.HOMBRE)
        tiposP1.add(PeluqueroType.KIDS)

        tiposP2.add(PeluqueroType.MUJER)
        tiposP2.add(PeluqueroType.HOMBRE)
        tiposP2.add(PeluqueroType.KIDS)

        val peluquero1 = Peluquero.Builder().
                                withTipos(tiposP1).
                                build()

        val peluquero2 = Peluquero.Builder().
                                withTipos(tiposP2).
                                build()

        peluquero1.eliminarTipo(PeluqueroType.KIDS)

        peluquero2.eliminarTipo(PeluqueroType.HOMBRE)

        assertEquals(peluquero1.tipos.size,2)
        assertEquals(peluquero2.tipos.size,2)

        assertFalse(peluquero1.tipos.contains(PeluqueroType.KIDS))
        assertTrue(peluquero1.tipos.contains(PeluqueroType.MUJER))
        assertTrue(peluquero1.tipos.contains(PeluqueroType.HOMBRE))

        assertFalse(peluquero2.tipos.contains(PeluqueroType.HOMBRE))
        assertTrue(peluquero2.tipos.contains(PeluqueroType.MUJER))
        assertTrue(peluquero2.tipos.contains(PeluqueroType.KIDS))

    }

    @Test
    fun testPeluqueroDisponibleRetornaTrueEnTieneTurnoEnEsperaYEstaDisponiblePeroFalseEnEstaOcupado_EstaDesconectadoYEstaDeshabilitado(){
        val peluquero = Peluquero.Builder().
                            withEstado(PeluqueroState.DISPONIBLE).
                            build()

        assertTrue(peluquero.getEstaDisponible())
        assertTrue(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaOcupado())
        assertFalse(peluquero.getEstaDeshabilitado())
        assertFalse(peluquero.getEstaDesconectado())
    }

    @Test
    fun testPeluqueroDisponibleSinTurnoRetornaTrueEnEstaDisponiblePeroFalseEnEstaTieneTurnoEnEspera_Ocupado_EstaDesconectadoYEstaDeshabilitado(){
        val peluquero = Peluquero.Builder().
        withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO).
        build()

        assertTrue(peluquero.getEstaDisponible())
        assertFalse(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaOcupado())
        assertFalse(peluquero.getEstaDeshabilitado())
        assertFalse(peluquero.getEstaDesconectado())
    }

    @Test
    fun testPeluqueroDisponibleRetornaTrueEnTieneTurnoEnEsperaYEstaOcupadoPeroFalseEnEstaDisponible_EstaDesconectadoYEstaDeshabilitado(){
        val peluquero = Peluquero.Builder().
                            withEstado(PeluqueroState.OCUPADO).
                            build()

        assertTrue(peluquero.getEstaOcupado())
        assertTrue(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaDisponible())
        assertFalse(peluquero.getEstaDeshabilitado())
        assertFalse(peluquero.getEstaDesconectado())
    }

    @Test
    fun testPeluqueroOcupadoSinTurnoRetornaTrueEnEstaOcupadoPeroFalseEnEstaTieneTurnoEnEspera_Disponible_EstaDesconectadoYEstaDeshabilitado(){
        val peluquero = Peluquero.Builder().
                            withEstado(PeluqueroState.OCUPADO_SIN_TURNO).
                            build()

        assertTrue(peluquero.getEstaOcupado())
        assertFalse(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaDisponible())
        assertFalse(peluquero.getEstaDeshabilitado())
        assertFalse(peluquero.getEstaDesconectado())
    }

    @Test
    fun testPeluqueroDesconectadoRetornaTrueEnEstaDesconectadoPeroFalseEnEstaTieneTurnoEnEspera_Disponible_EstaOcupadoYEstaDeshabilitado(){
        val peluquero = Peluquero.Builder().
                                withEstado(PeluqueroState.DESCONECTADO).
                                build()

        assertTrue(peluquero.getEstaDesconectado())
        assertFalse(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaDisponible())
        assertFalse(peluquero.getEstaDeshabilitado())
        assertFalse(peluquero.getEstaOcupado())
    }

    @Test
    fun testPeluqueroDeshabilitadoRetornaTrueEnEstaDeshabilitadoPeroFalseEnEstaTieneTurnoEnEspera_Disponible_EstaOcupadoYEstaDesconectado(){
        val peluquero = Peluquero.Builder().
                                withEstado(PeluqueroState.DESHABILITADO).
                                build()

        assertTrue(peluquero.getEstaDeshabilitado())
        assertFalse(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaDisponible())
        assertFalse(peluquero.getEstaDesconectado())
        assertFalse(peluquero.getEstaOcupado())
    }

    @Test
    fun testPeluqueroDisponibleODisponibleSinTurnoAlDesconectarseCambiaElEstadoDelPeluqueroADesconectado(){

        val peluquero1 = Peluquero.Builder().
                                    withEstado(PeluqueroState.DISPONIBLE).
                                    build()
        val peluquero2 = Peluquero.Builder().
                                    withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO).
                                    build()

        peluquero1.desconectar()
        peluquero2.desconectar()

        assertTrue(peluquero1.getEstaDesconectado())
        assertTrue(peluquero2.getEstaDesconectado())
    }

    @Test
    fun testPeluqueroQueNoPoseaElEstadoDisponibleODisponibleSinTurnoAlDesconectarseNoHaceNadaEsDecirMantieneSuEstado(){

        val peluquero1 = Peluquero.Builder().
                                withEstado(PeluqueroState.OCUPADO).
                                build()
        val peluquero2 = Peluquero.Builder().
                                withEstado(PeluqueroState.OCUPADO_SIN_TURNO).
                                build()

        val peluquero3 = Peluquero.Builder()
                                .withEstado(PeluqueroState.DESCONECTADO)
                                .build()

        val peluquero4 = Peluquero.Builder()
                                    .withEstado(PeluqueroState.DESHABILITADO)
                                    .build()

        peluquero1.desconectar()
        peluquero2.desconectar()
        peluquero3.desconectar()
        peluquero4.desconectar()

        assertFalse(peluquero1.getEstaDesconectado())
        assertFalse(peluquero2.getEstaDesconectado())
        assertFalse(peluquero4.getEstaDesconectado())

        assertTrue(peluquero1.getEstaOcupado())
        assertTrue(peluquero1.getTieneTurnosEnEspera())
        assertTrue(peluquero2.getEstaOcupado())
        assertTrue(peluquero3.getEstaDesconectado())
        assertTrue(peluquero4.getEstaDeshabilitado())
    }

    @Test
    fun testPeluqueroDesconectadoAlConectarCambiaSuEstadoADisponibleYActualizaSuUltimoLoginAlLaFechaActual(){
        val lastLoginDate = LocalDateTime.now().minusDays(2)
        val peluquero = Peluquero.Builder().
                            withEstado(PeluqueroState.DESCONECTADO).
                            withUltimoLogin(lastLoginDate).
                            build()

        peluquero.conectar()

        assertTrue(peluquero.getEstaDisponible())
        assertFalse(peluquero.getEstaDesconectado())

        assertNotEquals(peluquero.ultimoLogin,lastLoginDate)

        //TODO
        // BUSCAR COMO HACER DELTA CON LA FECHA DE AHORA
        assertTrue(peluquero.ultimoLogin.isAfter(lastLoginDate))
    }

    @Test
    fun testPeluqueroConCualquierEstadoMenosDesconectadoAlConectarNoHaceNadaNiCambiaSuLastLoginDate(){
        val lastLoginDate = LocalDateTime.now().minusDays(3)

        val peluquero1 = Peluquero.Builder()
                .withUltimoLogin(lastLoginDate)
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withUltimoLogin(lastLoginDate)
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withUltimoLogin(lastLoginDate)
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withUltimoLogin(lastLoginDate)
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        val peluquero5 = Peluquero.Builder()
                .withUltimoLogin(lastLoginDate)
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        peluquero1.conectar()
        peluquero2.conectar()
        peluquero3.conectar()
        peluquero4.conectar()
        peluquero5.conectar()

        assertTrue(peluquero1.getEstaDisponible())
        assertTrue(peluquero1.getTieneTurnosEnEspera())
        assertTrue(peluquero2.getEstaDisponible())
        assertTrue(peluquero3.getEstaOcupado())
        assertTrue(peluquero3.getTieneTurnosEnEspera())
        assertTrue(peluquero4.getEstaOcupado())
        assertTrue(peluquero5.getEstaDeshabilitado())

        assertEquals(peluquero1.ultimoLogin,lastLoginDate)
        assertEquals(peluquero2.ultimoLogin,lastLoginDate)
        assertEquals(peluquero3.ultimoLogin,lastLoginDate)
        assertEquals(peluquero4.ultimoLogin,lastLoginDate)
        assertEquals(peluquero5.ultimoLogin,lastLoginDate)
    }

    @Test
    fun testPeluqueroDisponibleAlOcuparCambiaSuEstadoAOcupado(){
        val peluquero = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        peluquero.ocupar()

        assertTrue(peluquero.getEstaOcupado())
        assertTrue(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaDisponible())
    }

    @Test
    fun testPeluqueroDisponibleSinTurnoAlOcuparCambiaSuEstadoAOcupadoSinTurno(){
        val peluquero = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        peluquero.ocupar()

        assertTrue(peluquero.getEstaOcupado())
        assertFalse(peluquero.getTieneTurnosEnEspera())
        assertFalse(peluquero.getEstaDisponible())
    }

    @Test
    fun testCualquierPeluqueroMenosDisponibleODisponibleSinTurnoARecibirOcuparNoHaceNada(){
        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESCONECTADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        peluquero1.ocupar()
        peluquero2.ocupar()
        peluquero3.ocupar()
        peluquero4.ocupar()

        assertTrue(peluquero1.getEstaDesconectado())
        assertTrue(peluquero2.getEstaOcupado())
        assertTrue(peluquero2.getTieneTurnosEnEspera())
        assertTrue(peluquero3.getEstaOcupado())
        assertTrue(peluquero4.getEstaDeshabilitado())
    }

    @Test
    fun testPeluqueroOcupadoOOcupadoSinTurnoAlDesocuparCambianSuEstadoADisponible(){
        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        peluquero1.desocupar()
        peluquero2.desocupar()

        assertTrue(peluquero1.getEstaDisponible())
        assertTrue(peluquero2.getEstaDisponible())
    }

    @Test
    fun testCualquierPeluqueroQueNoSeaOcupadoUOcupadoSinTurnoAlDesocuparNoHaceNada() {
        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESCONECTADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        peluquero1.desocupar()
        peluquero2.desocupar()
        peluquero3.desocupar()
        peluquero4.desocupar()

        assertTrue(peluquero1.getEstaDesconectado())
        assertTrue(peluquero2.getEstaDisponible())
        assertTrue(peluquero2.getTieneTurnosEnEspera())
        assertTrue(peluquero3.getEstaDisponible())
        assertFalse(peluquero3.getTieneTurnosEnEspera())
        assertTrue(peluquero4.getEstaDeshabilitado())
    }

    @Test
    fun testPeluqueroDeshabilitadoAlDeshabilitarNoHaceNadaYMantieneSuFechaDeshabilitacionYVecesDeshabilitadoIgual(){
        val vecesDeshabilitado = 1
        val fechaDeshabilitacion = LocalDateTime.now().minusDays(2)
        val peluquero = Peluquero.Builder()
                .withVecesDeshabilitado(vecesDeshabilitado)
                .withFechaDeshabilitacion(fechaDeshabilitacion)
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        peluquero.deshabilitar()

        assertTrue(peluquero.getEstaDeshabilitado())
        assertEquals(peluquero.fechaDeshabilitacion,fechaDeshabilitacion)
        assertEquals(peluquero.vecesDeshabilitado,vecesDeshabilitado)
    }

    @Test
    fun testCualquierPeluqueroNoDeshabilitadoAlDeshabilitarAumentaEnUnoSusVecesDeshabilitadoYActualizaLaFechaDeDeshabilitacionALaActualYCambiaSuEstadoADeshabilitado(){

        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESCONECTADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withVecesDeshabilitado(1)
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withVecesDeshabilitado(2)
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        val peluquero5 = Peluquero.Builder()
                .withVecesDeshabilitado(5)
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        peluquero1.deshabilitar()
        peluquero2.deshabilitar()
        peluquero3.deshabilitar()
        peluquero4.deshabilitar()
        peluquero5.deshabilitar()

        assertTrue(peluquero1.getEstaDeshabilitado())
        assertTrue(peluquero2.getEstaDeshabilitado())
        assertTrue(peluquero3.getEstaDeshabilitado())
        assertTrue(peluquero4.getEstaDeshabilitado())
        assertTrue(peluquero5.getEstaDeshabilitado())

        assertEquals(peluquero1.vecesDeshabilitado,1)
        assertEquals(peluquero2.vecesDeshabilitado,2)
        assertEquals(peluquero3.vecesDeshabilitado,3)
        assertEquals(peluquero4.vecesDeshabilitado,1)
        assertEquals(peluquero5.vecesDeshabilitado,6)

        assertTrue(estaDentroDelRangoDeFechaActual(peluquero1.fechaDeshabilitacion!!))
        assertTrue(estaDentroDelRangoDeFechaActual(peluquero2.fechaDeshabilitacion!!))
        assertTrue(estaDentroDelRangoDeFechaActual(peluquero3.fechaDeshabilitacion!!))
        assertTrue(estaDentroDelRangoDeFechaActual(peluquero4.fechaDeshabilitacion!!))
        assertTrue(estaDentroDelRangoDeFechaActual(peluquero5.fechaDeshabilitacion!!))
    }

    @Test
    fun testPeluqueroDeshabilitadoAlRecibirHabilitarCambiaSuEstadoADisponiblePeroConservaLaFechaCuandoFueDeshabilitadoYLasVecesQueFueDeshabilitado(){
        val fechaDeshabilitacion = LocalDateTime.now().minusDays(2)
        val peluquero = Peluquero.Builder()
                .withVecesDeshabilitado(2)
                .withFechaDeshabilitacion(fechaDeshabilitacion)
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        peluquero.habilitar()

        assertTrue(peluquero.getEstaDisponible())
        assertTrue(peluquero.getTieneTurnosEnEspera())
        assertEquals(peluquero.vecesDeshabilitado,2)
        assertEquals(peluquero.fechaDeshabilitacion,fechaDeshabilitacion)
    }

    @Test
    fun testCualquierPeluqueroMenosElDeshabilitadoAlRecibirHabilitarNoHaceNada(){
        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESCONECTADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        val peluquero5 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        peluquero1.habilitar()
        peluquero2.habilitar()
        peluquero3.habilitar()
        peluquero4.habilitar()
        peluquero5.habilitar()

        assertTrue(peluquero1.getEstaDesconectado())
        assertTrue(peluquero2.getEstaDisponible())
        assertTrue(peluquero2.getTieneTurnosEnEspera())
        assertTrue(peluquero3.getEstaDisponible())
        assertTrue(peluquero4.getEstaOcupado())
        assertTrue(peluquero4.getTieneTurnosEnEspera())
        assertTrue(peluquero5.getEstaOcupado())
    }

    @Test
    fun testPeluqueroDisponibleAlRecibirNoPoseeMasTurnosCambiaSuEstadoADisponibleSinTurnos(){
        val peluquero = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        peluquero.noPoseeMasTurnos()

        assertTrue(peluquero.getEstaDisponible())
        assertFalse(peluquero.getTieneTurnosEnEspera())
    }

    @Test
    fun testPeluqueroOcupadoAlRecibirNoPoseeMasTurnosCambiaSuEstadoAOcupadoSinTurnos(){
        val peluquero = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        peluquero.noPoseeMasTurnos()

        assertTrue(peluquero.getEstaOcupado())
        assertFalse(peluquero.getTieneTurnosEnEspera())
    }

    @Test
    fun testCualquierPeluqueroQueNoPoseaExactamenteElEstadoDisponibleUOcupadoAlRecibirNoPoseeMasTurnosNoPasaNada(){
        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESCONECTADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        peluquero1.noPoseeMasTurnos()
        peluquero2.noPoseeMasTurnos()
        peluquero3.noPoseeMasTurnos()
        peluquero4.noPoseeMasTurnos()

        assertTrue(peluquero1.getEstaDesconectado())

        assertTrue(peluquero2.getEstaDisponible())
        assertFalse(peluquero2.getTieneTurnosEnEspera())

        assertTrue(peluquero3.getEstaDeshabilitado())

        assertTrue(peluquero4.getEstaOcupado())
        assertFalse(peluquero4.getTieneTurnosEnEspera())
    }

    @Test
    fun testPeluqueroDisponibleSinTurnoAlRecibirPoseeTurnosCambiaSuEstadoADisponible(){
        val peluquero = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE_SIN_TURNO)
                .build()

        peluquero.poseeTurnos()

        assertTrue(peluquero.getEstaDisponible())
        assertTrue(peluquero.getTieneTurnosEnEspera())
    }

    @Test
    fun testPeluqueroOcupadoSinTurnoAlRecibirPoseeTurnosCambiaSuEstadoAOcupado(){
        val peluquero = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO_SIN_TURNO)
                .build()

        peluquero.poseeTurnos()

        assertTrue(peluquero.getEstaOcupado())
        assertTrue(peluquero.getTieneTurnosEnEspera())
    }

    @Test
    fun testCualquierPeluqueroQueNoSeaDisponibleSinTurnoUOcupadoSinTurnoAlRecibirPoseeTurnosNoHaceNada(){
        val peluquero1 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESCONECTADO)
                .build()

        val peluquero2 = Peluquero.Builder()
                .withEstado(PeluqueroState.DISPONIBLE)
                .build()

        val peluquero3 = Peluquero.Builder()
                .withEstado(PeluqueroState.DESHABILITADO)
                .build()

        val peluquero4 = Peluquero.Builder()
                .withEstado(PeluqueroState.OCUPADO)
                .build()

        peluquero1.poseeTurnos()
        peluquero2.poseeTurnos()
        peluquero3.poseeTurnos()
        peluquero4.poseeTurnos()

        assertTrue(peluquero1.getEstaDesconectado())
        assertTrue(peluquero2.getEstaDisponible())
        assertTrue(peluquero2.getTieneTurnosEnEspera())
        assertTrue(peluquero3.getEstaDeshabilitado())
        assertTrue(peluquero4.getEstaOcupado())
        assertTrue(peluquero4.getTieneTurnosEnEspera())
    }

    private fun estaDentroDelRangoDeFechaActual(fecha: LocalDateTime):Boolean {
        val fechaActualMenosDiezSegundos = LocalDateTime.now().minusSeconds(10)
        val fechaActualMas1Minutos = LocalDateTime.now().plusMinutes(1)
        return fecha.isAfter(fechaActualMenosDiezSegundos) && fecha.isBefore(fechaActualMas1Minutos)
    }
}