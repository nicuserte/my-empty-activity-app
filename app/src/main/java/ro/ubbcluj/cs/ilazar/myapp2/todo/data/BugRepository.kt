package ro.ubbcluj.cs.ilazar.myapp2.todo.data

import android.util.Log
import ro.ubbcluj.cs.ilazar.myapp2.core.TAG
import ro.ubbcluj.cs.ilazar.myapp2.todo.data.remote.BugApi

object BugRepository {
    private var cachedBugs: MutableList<Bug>? = null;

    suspend fun loadAll(): List<Bug> {
        Log.i(TAG, "loadAll")
        if (cachedBugs != null) {
            return cachedBugs as List<Bug>;
        }
        cachedBugs = mutableListOf()
        val items = BugApi.service.find()
        cachedBugs?.addAll(items)
        return cachedBugs as List<Bug>
    }

    suspend fun load(itemId: String): Bug {
        Log.i(TAG, "load")
        val item = cachedBugs?.find { it.id == itemId }
        if (item != null) {
            return item
        }
        return BugApi.service.read(itemId)
    }

    suspend fun save(bug: Bug): Bug {
        Log.i(TAG, "save")
        val createdItem = BugApi.service.create(bug)
        cachedBugs?.add(createdItem)
        return createdItem
    }

    suspend fun update(bug: Bug): Bug {
        Log.i(TAG, "update")
        val updatedItem = BugApi.service.update(bug.id, bug)
        val index = cachedBugs?.indexOfFirst { it.id == bug.id }
        if (index != null) {
            cachedBugs?.set(index, updatedItem)
        }
        return updatedItem
    }
}