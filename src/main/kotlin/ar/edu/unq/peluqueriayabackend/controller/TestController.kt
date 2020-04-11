package ar.edu.unq.peluqueriayabackend.controller


import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
class TestController() {

    @PostMapping
    @RequestMapping("/")
    fun testAPI() = "API run!"
}