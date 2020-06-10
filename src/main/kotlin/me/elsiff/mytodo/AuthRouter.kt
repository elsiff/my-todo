package me.elsiff.mytodo

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

/**
 * Created by elsiff on 2020-06-11.
 */
@Component
class AuthRouter(
    private val authHandler: AuthHandler
) {
    @Bean
    fun authRoutes() = router {
        "/auth".nest {
            POST("/sign-in", authHandler::signIn)
            POST("/sign-up", authHandler::signUp)
        }
    }
}