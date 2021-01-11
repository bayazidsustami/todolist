package id.todolistapp.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(){
        Database.connect(
            url = "jdbc:mysql://127.0.0.1:3306/todo_db",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = "")
    }

    suspend fun <T> dbQuery(block:() -> T): T{
        return withContext(Dispatchers.IO){
            transaction { block() }
        }
    }
}