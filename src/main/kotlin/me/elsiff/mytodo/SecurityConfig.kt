package me.elsiff.mytodo

import me.elsiff.mytodo.auth.jwt.JwtAuthenticationManager
import me.elsiff.mytodo.auth.jwt.JwtServerAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint

/**
 * Created by elsiff on 2020-06-10.
 */
@Configuration
class SecurityConfig {
    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        jwtAuthenticationManager: JwtAuthenticationManager,
        jwtServerAuthenticationConverter: JwtServerAuthenticationConverter
    ): SecurityWebFilterChain {
        val authenticationWebFilter = AuthenticationWebFilter(jwtAuthenticationManager)
        authenticationWebFilter.setServerAuthenticationConverter(jwtServerAuthenticationConverter)

        return http.authorizeExchange()
            .pathMatchers("/auth/sign-up")
            .permitAll()
            .pathMatchers("/auth/sign-in")
            .permitAll()
            .pathMatchers("/users/**")
            .authenticated()
            .pathMatchers("/tasks/**")
            .authenticated()
            .and()
            .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic()
            .disable()
            .csrf()
            .disable()
            .formLogin()
            .disable()
            .logout()
            .disable()
            .build()
    }
}