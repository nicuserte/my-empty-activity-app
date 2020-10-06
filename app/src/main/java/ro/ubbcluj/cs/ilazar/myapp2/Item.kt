package ro.ubbcluj.cs.ilazar.myapp2

data class Item(
    val id: String,
    val text: String
) {
    override fun toString(): String = text
}
