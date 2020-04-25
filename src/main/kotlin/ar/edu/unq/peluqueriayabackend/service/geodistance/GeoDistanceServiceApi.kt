package ar.edu.unq.peluqueriayabackend.service.geodistance

import ar.edu.unq.peluqueriayabackend.model.Ubicacion

interface GeoDistanceServiceApi {
    fun distanciaCoordEnKM(lat1:Double,lng1:Double,lat2:Double,lng2:Double):Double
    fun distanciaCoordEnMillas(lat1:Double,lng1:Double,lat2:Double,lng2:Double):Double
    fun distanciaUbicacionesEnKM(ubicacion1: Ubicacion, ubicacion2: Ubicacion):Double
    fun distanciaUbicacionesEnMillas(ubicacion1: Ubicacion, ubicacion2: Ubicacion):Double
}