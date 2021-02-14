package org.michaelbel.todo.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import org.michaelbel.todo.data.entity.TodoData
import org.michaelbel.todo.databinding.CardItemBinding

class TodoPagingAdapter: PagingDataAdapter<TodoData, TodoViewHolder>(TodoDiffUtilComparator) {

    interface ClickListener {
        fun onTodoClick(it: TodoData)
    }

    var clickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo: TodoData? = getItem(position)
        todo?.let { todoData ->
            holder.bind(todoData)
            holder.binding.root.setOnClickListener { clickListener?.onTodoClick(todoData) }
        }
    }

    fun getTodoItem(position: Int): TodoData? {
        return getItem(position)
    }
}