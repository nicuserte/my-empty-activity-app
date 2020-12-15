package ro.ubbcluj.cs.ilazar.myapp2.todo.bugs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_bug.view.*
import ro.ubbcluj.cs.ilazar.myapp2.R
import ro.ubbcluj.cs.ilazar.myapp2.core.TAG
import ro.ubbcluj.cs.ilazar.myapp2.todo.data.Bug
import ro.ubbcluj.cs.ilazar.myapp2.todo.bug.BugEditFragment

class BugListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<BugListAdapter.ViewHolder>() {

    var items = emptyList<Bug>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onItemClick: View.OnClickListener;

    init {
        onItemClick = View.OnClickListener { view ->
            val item = view.tag as Bug
            fragment.findNavController().navigate(R.id.ItemEditFragment, Bundle().apply {
                putString(BugEditFragment.ITEM_ID, item.id)
                putString(BugEditFragment.ITEM_TITLE, item.title)
                putString(BugEditFragment.ITEM_DESCRIPTION, item.description)
                putString(BugEditFragment.ITEM_PRIORITY, item.priority.toString())
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_bug, parent, false)
        Log.v(TAG, "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.v(TAG, "onBindViewHolder $position")
        val item = items[position]
        holder.itemView.tag = item
        holder.textView.text = item.title + " " + item.description + " " + item.priority
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.text
    }
}
