package me.elsiff.mytodo.auth.jwt

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

/**
 * Created by elsiff on 2020-06-11.
 */
@Component
class JwtAuthenticationManager(
    private val jwtSigner: JwtSigner
) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication)
            .map { jwtSigner.validateJwt(it.credentials as String) }
            .onErrorResume { error ->
                Mono.error(
                    AuthenticationCredentialsNotFoundException(error.message, error)
                )
            }
            .map { jws ->
                UsernamePasswordAuthenticationToken(
                    jws.body.subject,
                    authentication.credentials as String,
                    mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
                )
            }
    }
}