package ar.edu.unq.peluqueriayabackend.model

import java.time.LocalDateTime

enum class PeluqueroState {
    DISPONIBLE{
        override fun desconectar(peluquero: Peluquero) {
            peluquero.estado = DESCONECTADO
        }
        override fun ocupar(peluquero: Peluquero) {
            peluquero.estado = OCUPADO
        }
        override fun deshabilitar(peluquero: Peluquero) {
            procedimientoDeshabilitar(peluquero)
        }
        override fun noPoseeMasTurnos(peluquero: Peluquero) {
            peluquero.estado = DISPONIBLE_SIN_TURNO
        }
        override fun tieneTurnosEnEspera():Boolean = true
        override fun estaDisponible() : Boolean = true
    },
    OCUPADO{
        override fun desocupar(peluquero: Peluquero) {
            peluquero.estado = DISPONIBLE
        }
        override fun deshabilitar(peluquero: Peluquero) {
            procedimientoDeshabilitar(peluquero)
        }
        override fun noPoseeMasTurnos(peluquero: Peluquero) {
            peluquero.estado = OCUPADO_SIN_TURNO
        }
        override fun tieneTurnosEnEspera():Boolean = true
        override fun estaOcupado() : Boolean = true
    },
    DESCONECTADO{
        override fun conectar(peluquero: Peluquero) {
            peluquero.ultimoLogin = LocalDateTime.now()
            peluquero.estado = DISPONIBLE
        }
        override fun deshabilitar(peluquero: Peluquero) {
            procedimientoDeshabilitar(peluquero)
        }
        override fun estaDesconectado() : Boolean = true
    },
    DESHABILITADO{
        override fun habilitar(peluquero: Peluquero) {
            peluquero.estado = DISPONIBLE
        }
        override fun estaDeshabilitado() : Boolean = true
    },
    DISPONIBLE_SIN_TURNO {
        override fun desconectar(peluquero: Peluquero) {
            peluquero.estado = DESCONECTADO
        }
        override fun ocupar(peluquero: Peluquero) {
            peluquero.estado = OCUPADO_SIN_TURNO
        }
        override fun deshabilitar(peluquero: Peluquero) {
            procedimientoDeshabilitar(peluquero)
        }
        override fun poseeTurnos(peluquero: Peluquero) {
            peluquero.estado = DISPONIBLE
        }
        override fun estaDisponible() : Boolean = true
    },
    OCUPADO_SIN_TURNO {
        override fun desocupar(peluquero: Peluquero) {
            peluquero.estado = DISPONIBLE
        }
        override fun deshabilitar(peluquero: Peluquero) {
            procedimientoDeshabilitar(peluquero)
        }
        override fun poseeTurnos(peluquero: Peluquero) {
            peluquero.estado = OCUPADO
        }
        override fun estaOcupado() : Boolean = true
    };

    //DEFAULT METHODS (DO NOTHING OR RETURNS FALSE)
    open fun desconectar(peluquero: Peluquero) {}

    open fun conectar(peluquero: Peluquero) {}

    open fun ocupar(peluquero: Peluquero) {}

    open fun desocupar(peluquero: Peluquero) {}

    open fun deshabilitar(peluquero: Peluquero) {}

    open fun habilitar(peluquero: Peluquero) {}

    open fun noPoseeMasTurnos(peluquero: Peluquero) {}

    open fun poseeTurnos(peluquero: Peluquero) {}

    open fun tieneTurnosEnEspera():Boolean = false

    open fun estaDisponible() : Boolean = false

    open fun estaOcupado() : Boolean = false

    open fun estaDesconectado() : Boolean = false

    open fun estaDeshabilitado() : Boolean = false

    protected fun procedimientoDeshabilitar(peluquero: Peluquero) {
        peluquero.fechaDeshabilitacion = LocalDateTime.now()
        peluquero.vecesDeshabilitado = peluquero.vecesDeshabilitado + 1
        peluquero.estado = DESHABILITADO
    }
}