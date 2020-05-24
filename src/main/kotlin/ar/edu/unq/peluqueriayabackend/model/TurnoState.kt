package ar.edu.unq.peluqueriayabackend.model

import java.time.LocalDateTime

enum class TurnoState {

    PENDIENTE {
        override fun cancelar(turno: Turno) {
            turno.fechaCancelacion = LocalDateTime.now()
            turno.estado = CANCELADO
        }
        override fun confirmar(turno:Turno) {
            turno.fechaConfirmacion = LocalDateTime.now()
            turno.estado = CONFIRMADO
        }
        override fun estaPendiente():Boolean = true
    },
    CONFIRMADO {
        override fun finalizar(turno: Turno) {
            turno.fechaFin = LocalDateTime.now()
            turno.estado = FINALIZADO
        }
        override fun estaConfirmado():Boolean = true
    },
    FINALIZADO{
        override fun puntuar(valor:Int, turno:Turno) {
            turno.puntaje = valor
        }
        override fun estaFinalizado(): Boolean = true
    },
    CANCELADO {
        override fun estaCancelado() : Boolean = true
    },
    ESPERANDO {
        override fun cancelar(turno: Turno) {
            turno.fechaCancelacion = LocalDateTime.now()
            turno.estado = CANCELADO
        }
        override fun terminarEsperaTurno(turno: Turno) {
            turno.fechaPendiente = LocalDateTime.now()
            turno.estado = PENDIENTE
        }

        override fun estaEsperando(): Boolean = true
    };

    open fun puntuar(valor:Int, turno:Turno) {}
    open fun confirmar(turno:Turno) {}
    open fun finalizar(turno:Turno) {}
    open fun cancelar(turno: Turno) {}
    open fun terminarEsperaTurno(turno: Turno) {}
    open fun estaPendiente():Boolean = false
    open fun estaConfirmado():Boolean = false
    open fun estaFinalizado():Boolean = false
    open fun estaCancelado():Boolean = false
    open fun estaEsperando():Boolean = false
}