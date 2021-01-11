package id.todolistapp.routes

import id.todolistapp.model.NewTodoModel
import id.todolistapp.model.Response
import id.todolistapp.service.TodoService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.todo(todoService: TodoService){
    route("/todo"){
        get("getListAllTodo") {
            call.respond(
                mapOf("data" to todoService.getAllTodos()))
        }

        get("getTodoById/{id}") {
            val id = call.parameters["id"]?.toInt() ?:0

            val todo = todoService.getTodoById(id)
            if (todo == null) call.respond(HttpStatusCode.NotFound)
            else call.respond(todo)
        }

        post("addTodo") {
            val todo = call.receive<NewTodoModel>()
            val inserted = todoService.addTodo(todo)

            if (inserted != null){
                call.respond(inserted)
            } else {
                call.respond(HttpStatusCode.InternalServerError)
            }
        }

        put("update/{id}") {
            val id = call.parameters["id"]?.toInt() ?:0
            val todo = call.receive<NewTodoModel>()
            val updated = todoService.updateTodo(id, todo)

            if (updated != null) call.respond(updated)
            else call.respond(HttpStatusCode.NotFound)
        }

        delete("delete/{id}"){
            val id = call.parameters["id"]?.toInt() ?:0
            val success = todoService.deleteTodo(id)

            if (success) call.respond(Response("Delete Successfull"))
            else call.respond(HttpStatusCode.NotFound, "Delete Failed")
        }
    }
}