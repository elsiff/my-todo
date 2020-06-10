package me.elsiff.mytodo

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtSigner: JwtSigner
) : AuthService {
    override fun signIn(credentials: Mono<UserCredentials>): Mono<String> {
        return credentials
            .flatMap {
                userRepository.findByUsername(it.username)
                    .filter { user -> user.password == it.password }
            }
            .map {
                jwtSigner.createJwt(it.username)
            }
    }

    override fun signUp(user: Mono<User>) = userRepository.create(user)
}