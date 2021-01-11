package id.todolistapp.service

import id.todolistapp.utils.toModel
import id.todolistapp.model.NewTodoModel
import id.todolistapp.model.TodoModel
import id.todolistapp.model.TodoTable
import id.todolistapp.service.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

class TodoService {
    suspend fun getAllTodos(): List<TodoModel> = dbQuery{
        TodoTable.selectAll().orderBy(TodoTable.date to SortOrder.DESC).map { it.toModel() }
    }
    suspend fun getTodoById(id: Int): TodoModel? = dbQuery {
        TodoTable.select { TodoTable.id eq id }.mapNotNull { it.toModel() }.singleOrNull()
    }

    suspend fun addTodo(todo: NewTodoModel): TodoModel? {
        var key = 0
        dbQuery {
            key = (TodoTable.insert {
                it[name] = todo.title
                //it[date] = DateTime.parse(todo.date)
                it[done] = todo.done
            } get TodoTable.id)
        }

        return getTodoById(key)
    }

    suspend fun updateTodo(id: Int, todo: NewTodoModel): TodoModel? {
        dbQuery {
            TodoTable.update({ TodoTable.id eq id }) {
                it[name] = todo.title
                it[date] = DateTime(todo.date)
                it[done] = todo.done
            }
        }

        return getTodoById(id)
    }

    suspend fun deleteTodo(id: Int): Boolean = dbQuery {
        TodoTable.deleteWhere { TodoTable.id eq id } > 0
    }
}