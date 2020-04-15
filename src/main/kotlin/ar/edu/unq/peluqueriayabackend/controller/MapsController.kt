package ar.edu.unq.peluqueriayabackend.controller

import ar.edu.unq.peluqueriayabackend.service.MapsService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/maps")
class MapsController(val mapsService: MapsService) {

    @GetMapping("/autocomplete/coords")
    fun getLocationByCoords(@RequestParam latitude: String, @RequestParam longitude: String): Mono<Items> {
        val coords = "$latitude,$longitude"
        return mapsService.getLocationByCoords(coords)
    }

    @GetMapping("/autocomplete/address")
    fun getLocationByAddress(@RequestParam query: String): Flux<Items> {
        return mapsService.getLocationByAddress(query)
    }

}