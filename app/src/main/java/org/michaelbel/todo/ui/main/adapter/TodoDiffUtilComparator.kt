package org.michaelbel.todo.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import org.michaelbel.todo.data.entity.TodoData

object TodoDiffUtilComparator: DiffUtil.ItemCallback<TodoData>() {

    override fun areItemsTheSame(oldItem: TodoData, newItem: TodoData) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoData, newItem: TodoData) = oldItem == newItem
}