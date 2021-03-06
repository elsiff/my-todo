package me.elsiff.mytodo.auth

import me.elsiff.mytodo.user.User
import reactor.core.publisher.Mono

/**
 * Created by elsiff on 2020-06-10.
 */
interface AuthService {
    fun signIn(credentials: Mono<UserCredentials>): Mono<String>
    fun signUp(user: Mono<User>): Mono<User>
}