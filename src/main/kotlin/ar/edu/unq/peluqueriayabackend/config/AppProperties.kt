package ar.edu.unq.peluqueriayabackend.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class AppProperties(val hereMaps: HereMaps = HereMaps()) {
    data class HereMaps(var apiKey: String = "")
}
