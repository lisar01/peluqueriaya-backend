package ar.edu.unq.peluqueriayabackend.model

import java.time.LocalDateTime

enum class TurnoState {

    PENDIENTE {
        override fun puntuar(valor:Int, turno:Turno) {}
        override fun confirmarTurno(turno:Turno) {
            turno.fechaConfirmacion = LocalDateTime.now()
            turno.estado = CONFIRMADO
        }
        override fun finalizarTurno(turno: Turno) {}
    },
    CONFIRMADO {
        override fun puntuar(valor:Int, turno:Turno) {}
        override fun confirmarTurno(turno:Turno) {}
        override fun finalizarTurno(turno: Turno) {
            turno.fechaFin = LocalDateTime.now()
            turno.estado = FINALIZADO
        }
    },
    FINALIZADO{
        override fun puntuar(valor:Int, turno:Turno) {
            turno.puntaje = valor
        }
        override fun confirmarTurno(turno:Turno) {}
        override fun finalizarTurno(turno: Turno) {}
    };

    abstract fun puntuar(valor:Int, turno:Turno)
    abstract fun confirmarTurno(turno:Turno)
    abstract fun finalizarTurno(turno:Turno)
}