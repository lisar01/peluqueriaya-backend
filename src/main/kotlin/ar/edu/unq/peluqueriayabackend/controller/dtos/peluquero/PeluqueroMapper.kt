package ar.edu.unq.peluqueriayabackend.controller.dtos.peluquero

import ar.edu.unq.peluqueriayabackend.model.Peluquero

object PeluqueroMapper {

    fun dtoNuevoAPeluquero(email: String, nuevo: PeluqueroNuevoDTO): Peluquero =
            Peluquero.Builder()
                    .withNombre(nuevo.nombre)
                    .withLogo(nuevo.logo)
                    .withEmail(email)
                    .withEmailOpcional(nuevo.emailOpcional)
                    .withDescripcion(nuevo.descripcion)
                    .withDistanciaMax(nuevo.distanciaMax!!)
                    .withCorteMin(nuevo.corteMin!!)
                    .withUbicacion(nuevo.ubicacion)
                    .withTipos(nuevo.tipos.toMutableSet())
                    .build()

    fun dtoEditadoAPeluquero(peluqueroEditado: PeluqueroEditadoDTO, peluquero: Peluquero) {
        peluquero.nombre = peluqueroEditado.nombre ?: peluquero.nombre
        peluquero.descripcion = peluqueroEditado.descripcion ?: peluquero.descripcion
        peluquero.emailOpcional = peluqueroEditado.emailOpcional ?: peluquero.emailOpcional
        peluquero.logo = peluqueroEditado.logo ?: peluquero.logo
        peluquero.corteMin = peluqueroEditado.corteMin ?: peluquero.corteMin
        peluquero.distanciaMax = peluqueroEditado.distanciaMax ?: peluquero.distanciaMax
        peluquero.tipos = peluqueroEditado.tipos ?: peluquero.tipos
        peluquero.ubicacion = peluqueroEditado.ubicacion ?: peluquero.ubicacion
    }

    fun peluqueroSinPuntacionADto(
            puntuacion: Double?,
            peluqueroSinPuntuacion: PeluqueroPerfilSinPuntuacionDTO): PeluqueroPerfilDTO =
            PeluqueroPerfilDTO(
                    peluqueroSinPuntuacion.id,
                    peluqueroSinPuntuacion.logo,
                    peluqueroSinPuntuacion.nombre,
                    peluqueroSinPuntuacion.corteMin,
                    peluqueroSinPuntuacion.distanciaMax,
                    peluqueroSinPuntuacion.emailOpcional,
                    peluqueroSinPuntuacion.ubicacion,
                    peluqueroSinPuntuacion.descripcion,
                    peluqueroSinPuntuacion.tipos,
                    peluqueroSinPuntuacion.getEstaDesconectado(),
                    peluqueroSinPuntuacion.getEstaDisponible(),
                    puntuacion)

    fun peluqueroADto(peluquero: Peluquero): PeluqueroPerfilDTO =
            PeluqueroPerfilDTO(
                    peluquero.id,
                    peluquero.logo,
                    peluquero.nombre,
                    peluquero.corteMin,
                    peluquero.distanciaMax,
                    peluquero.emailOpcional,
                    peluquero.ubicacion,
                    peluquero.descripcion,
                    peluquero.tipos,
                    peluquero.getEstaDesconectado(),
                    peluquero.getEstaDisponible())

}