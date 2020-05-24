package ar.edu.unq.peluqueriayabackend.service.geodistance.impl

import ar.edu.unq.peluqueriayabackend.model.Ubicacion
import ar.edu.unq.peluqueriayabackend.service.geodistance.GeoDistanceServiceApi
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class GeoDistanceServiceImpl : GeoDistanceServiceApi {
    override fun distanciaCoordEnKM(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {

        val radioTierra = 6371.0 //en kilómetros

        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = Math.sin(dLat / 2)
        val sindLng = Math.sin(dLng / 2)
        val va1 = Math.pow(sindLat, 2.0) + (Math.pow(sindLng, 2.0)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)))
        val va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1))

        val resultadoCompleto = radioTierra * va2

        //Redondear valor con X decimales
        // val scale = Math.pow(10.0, X)
        val scale = Math.pow(10.0, 2.0)

        return (resultadoCompleto * scale).roundToInt() / scale
    }

    override fun distanciaCoordEnMillas(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        val radioTierra = 3958.75 //en millas
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = Math.sin(dLat / 2)
        val sindLng = Math.sin(dLng / 2)
        val va1 = Math.pow(sindLat, 2.0) + (Math.pow(sindLng, 2.0)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)))
        val va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1))

        val resultadoCompleto = radioTierra * va2

        //Redondear valor con X decimales
        // val scale = Math.pow(10.0, X)
        val scale = Math.pow(10.0, 2.0)

        return (resultadoCompleto * scale).roundToInt() / scale
    }

    override fun distanciaUbicacionesEnKM(ubicacion1: Ubicacion, ubicacion2: Ubicacion): Double =
            distanciaCoordEnKM(ubicacion1.getLatitudeAsDouble(),ubicacion1.getLongitudeAsDouble(),
                    ubicacion2.getLatitudeAsDouble(),ubicacion2.getLongitudeAsDouble())

    override fun distanciaUbicacionesEnMillas(ubicacion1: Ubicacion, ubicacion2: Ubicacion): Double =
            distanciaCoordEnMillas(ubicacion1.getLatitudeAsDouble(),ubicacion1.getLongitudeAsDouble(),
                    ubicacion2.getLatitudeAsDouble(),ubicacion2.getLongitudeAsDouble())
}