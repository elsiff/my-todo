package me.elsiff.mytodo.user

import me.elsiff.mytodo.user.User
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

/**
 * Created by elsiff on 2020-06-09.
 */
@Repository
class UserRepository(
    private val template: ReactiveMongoTemplate
) {
    fun create(user: Mono<User>) = template.save(user)
    fun findByUsername(username: String) = template.findOne<User>(
        Query(where("username").isEqualTo(username))
    )
    fun findAll() = template.findAll<User>()
    fun save(user: Mono<User>) = template.save(user)
    fun deleteByUsername(username: String) = template.remove<User>(
        Query(where("username").isEqualTo(username))
    )
}