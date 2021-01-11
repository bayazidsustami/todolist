package id.todolistapp.model

data class TodoModel(
    val id: Int,
    val title: String,
    val date: String,
    val done: Boolean
)