package ar.edu.unq.peluqueriayabackend.controller.dtos


data class Items(val items: List<Item>) {
    data class Item(val title: String, val position: Coords, val resultType: String) {
        data class Coords( val lat:String, val lng: String)
    }
}
