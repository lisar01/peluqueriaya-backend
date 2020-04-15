package ar.edu.unq.peluqueriayabackend.controller

import com.fasterxml.jackson.annotation.JsonProperty

data class Items(val items: List<Item>) {

    data class Item(val title: String, val position: Coords) {
        data class Coords(
                @JsonProperty("lat") val latitude:String,
                @JsonProperty("lng") val longitude: String
        )
    }
}
