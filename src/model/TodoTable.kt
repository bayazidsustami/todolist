package id.todolistapp.model

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.date

object TodoTable : Table("todo_list"){
    val id = integer("id").autoIncrement()
    val name = varchar("title", 255)
    val date = date("time_create")
    val done = bool("status")
    override val primaryKey = PrimaryKey(id)
}