package me.elsiff.mytodo.task

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

/**
 * Created by elsiff on 2020-06-22.
 */
@Component
class TaskRouter(
    private val taskHandler: TaskHandler
) {
    @Bean
    fun taskRoutes() = router {
        "/tasks".nest {
            GET("/{id}", taskHandler::get)
            GET("/", taskHandler::list)
            POST("/", taskHandler::post)
            PUT("/{id}", taskHandler::put)
            DELETE("/{id}", taskHandler::delete)
        }
    }
}