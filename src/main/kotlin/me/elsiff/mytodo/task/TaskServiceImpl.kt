package me.elsiff.mytodo.task

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository
) : TaskService {
    override fun getTask(id: UUID) = taskRepository.findById(id)

    override fun listTasks(username: String?) = taskRepository.find(username)

    override fun createTask(task: Mono<Task>) = taskRepository.create(task.map {
        it.id = UUID.randomUUID()
        it
    })

    override fun save(task: Mono<Task>) = taskRepository.save(task)

    override fun deleteTask(id: UUID) = taskRepository.deleteById(id).map { it.deletedCount > 0 }
}