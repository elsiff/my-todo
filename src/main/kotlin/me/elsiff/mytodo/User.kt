package me.elsiff.mytodo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by elsiff on 2020-06-09.
 */
@Document(collection = "Users")
data class User(
    @Id
    val username: String,
    var password: String,
    var email: String?
)