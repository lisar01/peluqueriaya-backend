package ar.edu.unq.peluqueriayabackend.service.geodistance

interface GeoDistanceServiceApi {
    fun distanciaCoordEnKM(lat1:Double,lng1:Double,lat2:Double,lng2:Double):Double
    fun distanciaCoordEnMillas(lat1:Double,lng1:Double,lat2:Double,lng2:Double):Double
}