package me.elsiff.mytodo.task

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.reactive.function.server.queryParamOrNull
import java.net.URI
import java.util.*

/**
 * Created by elsiff on 2020-06-22.
 */
@Component
class TaskHandler(
    private val taskService: TaskService
) {
    fun get(serverRequest: ServerRequest) =
        taskService.getTask(UUID.fromString(serverRequest.pathVariable("id")))
            .flatMap { ServerResponse.ok().bodyValue(it) }
            .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build())

    fun list(serverRequest: ServerRequest) =
        ServerResponse.ok().body(taskService.listTasks(serverRequest.queryParamOrNull("username")), Task::class.java)

    fun post(serverRequest: ServerRequest) =
        taskService.createTask(serverRequest.bodyToMono())
            .flatMap {
                ServerResponse.created(URI.create("/tasks/${it.id}")).build()
            }

    fun put(serverRequest: ServerRequest) =
        taskService.save(serverRequest.bodyToMono())
            .flatMap {
                ServerResponse.created(URI.create("/tasks/${it.id}")).build()
            }

    fun delete(serverRequest: ServerRequest) =
        taskService.deleteTask(UUID.fromString(serverRequest.pathVariable("id")))
            .flatMap { deleted ->
                if (deleted) ServerResponse.ok().build()
                else ServerResponse.status(HttpStatus.NOT_FOUND).build()
            }
}