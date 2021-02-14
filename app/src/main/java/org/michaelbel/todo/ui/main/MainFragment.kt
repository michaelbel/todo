package org.michaelbel.todo.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.michaelbel.todo.R
import org.michaelbel.todo.data.entity.TodoData
import org.michaelbel.todo.databinding.MainFragmentBinding
import org.michaelbel.todo.ui.base.BaseFragment
import org.michaelbel.todo.ui.edit.EditFragment
import org.michaelbel.todo.ui.main.adapter.TodoItemTouchHelper
import org.michaelbel.todo.ui.main.adapter.TodoPagingAdapter

@AndroidEntryPoint
class MainFragment: BaseFragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    private val adapter: TodoPagingAdapter by lazy {
        TodoPagingAdapter()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val todoItemTouchHelperCallback = object: TodoItemTouchHelper() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val itemToDelete = adapter.getTodoItem(viewHolder.bindingAdapterPosition)
            itemToDelete?.let { viewModel.deleteTodo(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MainFragmentBinding.bind(view)

        binding.toolbar.setOnClickListener {
            binding.recyclerView.smoothScrollToPosition(0)
        }

        adapter.addLoadStateListener {
            binding.emptyText.isVisible = adapter.itemCount == 0
        }
        adapter.clickListener = object: TodoPagingAdapter.ClickListener {
            override fun onTodoClick(it: TodoData) {
                parentFragmentManager.commit {
                    add(R.id.container, EditFragment.newInstance(it), "tag1")
                    addToBackStack("tag1")
                }
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val itemTouchHelper = ItemTouchHelper(todoItemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.fab.setOnClickListener {
            parentFragmentManager.commit {
                add(R.id.container, EditFragment.newInstance(), "tag2")
                addToBackStack("tag2")
            }
        }

        viewModel.getTodoList.subscribe {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }.autoDispose()
        viewModel.toastEvent.subscribe {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.autoDispose()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}