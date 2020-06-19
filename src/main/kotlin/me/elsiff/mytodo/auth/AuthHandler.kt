package me.elsiff.mytodo.auth

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToMono
import java.net.URI

/**
 * Created by elsiff on 2020-06-11.
 */
@Component
class AuthHandler(
    private val authService: AuthService
) {
    fun signIn(serverRequest: ServerRequest) =
        authService.signIn(serverRequest.bodyToMono())
            .flatMap { ok().bodyValue(it) }

    fun signUp(serverRequest: ServerRequest) =
        authService.signUp(serverRequest.bodyToMono())
            .flatMap {
                ServerResponse.created(URI.create("/users/${it.username}")).build()
            }
}