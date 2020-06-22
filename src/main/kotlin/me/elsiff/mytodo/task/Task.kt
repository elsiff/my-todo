package me.elsiff.mytodo.task

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
 * Created by elsiff on 2020-06-22.
 */
@Document(collection = "Tasks")
data class Task(
    @Id
    var id: UUID? = null,
    @Indexed
    val username: String,
    var title: String,
    var description: String,
    var checklist: MutableList<String>
)