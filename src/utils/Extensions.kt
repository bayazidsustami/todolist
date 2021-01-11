package id.todolistapp.utils

import id.todolistapp.model.TodoModel
import id.todolistapp.model.TodoTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toModel(): TodoModel {
    return TodoModel(
        this[TodoTable.id],
        this[TodoTable.name],
        this[TodoTable.date].toString(),
        this[TodoTable.done]
    )
}