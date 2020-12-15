package ro.ubbcluj.cs.ilazar.myapp2.todo.bug

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_bug_edit.*
import ro.ubbcluj.cs.ilazar.myapp2.R
import ro.ubbcluj.cs.ilazar.myapp2.core.TAG

class BugEditFragment : Fragment() {
    companion object {
        const val ITEM_ID = "ITEM_ID"
        const val ITEM_TITLE = "ITEM_TITLE"
        const val ITEM_DESCRIPTION = "ITEM_DESCRIPTION"
        const val ITEM_PRIORITY = "ITEM_PRIORITY"
    }

    private lateinit var viewModel: BugEditViewModel
    private var itemId: String? = null
    private var itemTitle: String? = null
    private var itemDescription: String? = null
    private var itemPriority: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate")
        arguments?.let {
            if (it.containsKey(ITEM_ID)) {
                itemId = it.getString(ITEM_ID).toString()
                itemTitle = it.getString(ITEM_TITLE).toString()
                itemDescription = it.getString(ITEM_DESCRIPTION).toString()
                itemPriority = it.getString(ITEM_PRIORITY).toString()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG, "onCreateView")
        return inflater.inflate(R.layout.fragment_bug_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(TAG, "onViewCreated")
        bug_title.setText(itemTitle)
        bug_description.setText(itemDescription)
        bug_priority.setText(itemPriority)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG, "onActivityCreated")
        setupViewModel()
        fab.setOnClickListener {
            Log.v(TAG, "save item")
//            viewModel.saveOrUpdateItem(item_text.text.toString())
            viewModel.saveOrUpdateItem(bug_title.text.toString(), bug_description.text.toString(), bug_priority.text.toString())
        }

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(BugEditViewModel::class.java)
        viewModel.bug.observe(viewLifecycleOwner, { item ->
            Log.v(TAG, "update items")
//            item_text.setText(item.description)
        })
        viewModel.fetching.observe(viewLifecycleOwner, { fetching ->
            Log.v(TAG, "update fetching")
            progress.visibility = if (fetching) View.VISIBLE else View.GONE
        })
        viewModel.fetchingError.observe(viewLifecycleOwner, { exception ->
            if (exception != null) {
                Log.v(TAG, "update fetching error")
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                Log.v(TAG, "completed, navigate back")
                findNavController().navigateUp()
            }
        })
        val id = itemId
        if (id != null) {
            viewModel.loadItem(id)
        }
    }
}
