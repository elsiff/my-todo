package me.elsiff.mytodo.user

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.*
import org.springframework.web.reactive.function.server.bodyToMono
import java.net.URI

/**
 * Created by elsiff on 2020-06-09.
 */
@Component
class UserHandler(
    private val userService: UserService
) {
    fun get(serverRequest: ServerRequest) =
        userService.getUser(serverRequest.pathVariable("username"))
            .flatMap { ok().bodyValue(it) }
            .switchIfEmpty(status(HttpStatus.NOT_FOUND).build())

    fun search(serverRequest: ServerRequest) =
        ok().body(userService.searchUsers(), User::class.java)

    fun post(serverRequest: ServerRequest) =
        userService.createUser(serverRequest.bodyToMono())
            .flatMap {
                created(URI.create("/users/${it.username}")).build()
            }

    fun put(serverRequest: ServerRequest) =
        userService.save(serverRequest.bodyToMono())
            .flatMap {
                created(URI.create("/users/${it.username}")).build()
            }

    fun delete(serverRequest: ServerRequest) =
        userService.deleteUser(serverRequest.pathVariable("username"))
            .flatMap { deleted ->
                if (deleted) ok().build()
                else status(HttpStatus.NOT_FOUND).build()
            }
}