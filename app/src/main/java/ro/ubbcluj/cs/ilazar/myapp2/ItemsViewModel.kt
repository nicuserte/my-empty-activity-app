package ro.ubbcluj.cs.ilazar.myapp

import java.util.ArrayList

object ItemsViewModel {
    val items: MutableList<Item> = ArrayList()

    private val COUNT = 100

    init {
        for (i in 1..COUNT) {
            addItem(createItem(i))
        }
    }

    private fun addItem(item: Item) {
        items.add(item)
    }

    private fun createItem(position: Int): Item {
        return Item(position.toString(), "Item " + position)
    }
}
