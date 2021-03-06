package me.elsiff.mytodo.user

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.annotation.PostConstruct

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    override fun getUser(username: String) = userRepository.findByUsername(username)

    override fun searchUsers() = userRepository.findAll()

    override fun createUser(user: Mono<User>) =
        userRepository.create(user.map {
            it.password = passwordEncoder().encode(it.password)
            it
        })

    override fun save(user: Mono<User>) = userRepository.save(user.map {
        it.password = passwordEncoder().encode(it.password)
        it
    })

    override fun deleteUser(username: String) =
        userRepository.deleteByUsername(username).map { it.deletedCount > 0 }

    @PostConstruct
    fun initializeRepository() =
        INITIAL_USERS.map(User::toMono)
            .map(this::createUser)
            .map(Mono<User>::subscribe)

    companion object {
        val INITIAL_USERS: List<User> = listOf(
            User("admin", "admin", "admin@my-todo.elsiff.me")
        )
    }
}