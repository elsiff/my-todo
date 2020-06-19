package me.elsiff.mytodo.user

import me.elsiff.mytodo.user.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

/**
 * Created by elsiff on 2020-06-09.
 */
@Component
class UserRouter(
    private val userHandler: UserHandler
) {
    @Bean
    fun userRoutes() = router {
        "/users".nest {
            GET("/{username}", userHandler::get)
            GET("/", userHandler::search)
            POST("/", userHandler::post)
            PUT("/{username}", userHandler::put)
            DELETE("/{username}", userHandler::delete)
        }
    }
}