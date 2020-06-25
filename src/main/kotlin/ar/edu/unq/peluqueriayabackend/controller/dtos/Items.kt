package ar.edu.unq.peluqueriayabackend.controller.dtos

import ar.edu.unq.peluqueriayabackend.model.Ubicacion


data class Items(val items: List<Item>) {
    data class Item(val title: String, val position: Coords, val resultType: String) {
        fun aUbicacion() = Ubicacion(title, position.lat, position.lng)
        data class Coords( val lat:String, val lng: String)
    }
}
