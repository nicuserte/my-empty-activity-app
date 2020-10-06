package ro.ubbcluj.cs.ilazar.myapp

data class Item(
    val id: String,
    val text: String
) {
    override fun toString(): String = text
}
