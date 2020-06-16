package ar.edu.unq.peluqueriayabackend.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*


@EnableWebSecurity
class SecurityConfig(
        @Value("\${auth0.audience}")
        val audience: String,
        @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
        val issuer: String) : WebSecurityConfigurerAdapter() {

    @Bean
    fun jwtDecoder(): JwtDecoder? {
        val jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer) as NimbusJwtDecoder
        val audienceValidator: OAuth2TokenValidator<Jwt> = AudienceValidator(audience)
        val withIssuer: OAuth2TokenValidator<Jwt> = JwtValidators.createDefaultWithIssuer(issuer)
        val withAudience: OAuth2TokenValidator<Jwt> = DelegatingOAuth2TokenValidator(withIssuer, audienceValidator)
        jwtDecoder.setJwtValidator(withAudience)
        return jwtDecoder
    }

    @Suppress("SpringElInspection")
    override fun configure(http: HttpSecurity) {
        val tieneRolCliente = "authenticated and @rolServiceImpl.tieneRolCliente()"
        val tieneRolPeluquero = "authenticated and @rolServiceImpl.tieneRolPeluquero()"

        //TODO poner las otras rutas
        http.cors(withDefaults()).authorizeRequests()
                .mvcMatchers(
                        "/swagger-ui.html/**",
                        "/configuration/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/webjars/**").permitAll()
                .mvcMatchers(
                        "/mapas/**",
                        "/servicio/tipos",
                        "/peluquero/search",
                        "/peluquero/{id}").permitAll()
                .mvcMatchers(HttpMethod.POST, "/peluquero", "/cliente").authenticated()
                .mvcMatchers("/roles", "/perfil").authenticated()
                .mvcMatchers(
                        "/turno/pedir",
                        "/turno/cancelar",
                        "/turno/cliente",
                        "/turno/calificar").access(tieneRolCliente)
                .mvcMatchers(
                        "/servicio",
                        "/peluquero/desconectar",
                        "/peluquero/conectar",
                        "/peluquero/servicios",
                        "/turno/peluquero",
                        "/turno/finalizar",
                        "/turno/confirmar").access(tieneRolPeluquero)
                .mvcMatchers(HttpMethod.GET, "/peluquero").access(tieneRolPeluquero)
                .mvcMatchers(HttpMethod.GET, "/cliente").access(tieneRolCliente)
                .anyRequest().denyAll()
                .and()
                .oauth2ResourceServer().jwt()
    }

}