package me.elsiff.mytodo.task

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 * Created by elsiff on 2020-06-22.
 */
interface TaskService {
    fun getTask(id: UUID): Mono<Task>
    fun listTasks(username: String?): Flux<Task>
    fun createTask(task: Mono<Task>): Mono<Task>
    fun save(task: Mono<Task>): Mono<Task>
    fun deleteTask(id: UUID): Mono<Boolean>
}