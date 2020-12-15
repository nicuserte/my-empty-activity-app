package ro.ubbcluj.cs.ilazar.myapp2.todo.data

data class Bug(
    val id: String,
    var description: String,
    var title: String,
    var priority: Int
) {
    override fun toString(): String = title
}
