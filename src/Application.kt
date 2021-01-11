package id.todolistapp

import id.todolistapp.routes.todo
import id.todolistapp.service.DatabaseFactory
import id.todolistapp.service.TodoService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    DatabaseFactory.init()

    val todoService = TodoService()
    install(Routing){
        todo(todoService)
    }
}

