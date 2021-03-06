package me.elsiff.mytodo.user

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Created by elsiff on 2020-06-09.
 */
interface UserService {
    fun getUser(username: String): Mono<User>
    fun searchUsers(): Flux<User>
    fun createUser(user: Mono<User>): Mono<User>
    fun save(user: Mono<User>): Mono<User>
    fun deleteUser(username: String): Mono<Boolean>
}