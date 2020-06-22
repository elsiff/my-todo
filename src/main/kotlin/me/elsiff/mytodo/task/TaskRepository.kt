package me.elsiff.mytodo.task

import me.elsiff.mytodo.user.User
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.util.*

/**
 * Created by elsiff on 2020-06-22.
 */
@Repository
class TaskRepository(
    private val template: ReactiveMongoTemplate
) {
    fun create(task: Mono<Task>) = template.save(task)

    fun findById(id: UUID) = template.findOne<Task>(
        Query(Criteria.where("id").isEqualTo(id))
    )

    fun find(username: String?) = template.find<Task>(
        if (username != null)
            Query(Criteria.where("username").isEqualTo(username))
        else
            Query()
    )

    fun save(task: Mono<Task>) = template.save(task)

    fun deleteById(id: UUID) = template.remove<Task>(
        Query(Criteria.where("id").isEqualTo(id))
    )
}