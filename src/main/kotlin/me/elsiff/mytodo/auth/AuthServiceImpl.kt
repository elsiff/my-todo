package me.elsiff.mytodo.auth

import me.elsiff.mytodo.auth.jwt.JwtSigner
import me.elsiff.mytodo.user.User
import me.elsiff.mytodo.user.UserRepository
import me.elsiff.mytodo.user.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl(
        private val userRepository: UserRepository,
        private val jwtSigner: JwtSigner,
        private val passwordEncoder: BCryptPasswordEncoder,
        private val userService: UserService
) : AuthService {
    override fun signIn(credentials: Mono<UserCredentials>): Mono<String> {
        return credentials
            .flatMap {
                userRepository.findByUsername(it.username)
                    .filter { user -> passwordEncoder.matches(it.password, user.password) }
            }
            .map {
                jwtSigner.createJwt(it.username)
            }
    }

    override fun signUp(user: Mono<User>) = userService.createUser(user)
}