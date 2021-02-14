package org.michaelbel.todo.ui.main.adapter

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.michaelbel.todo.R
import org.michaelbel.todo.data.entity.Priority
import org.michaelbel.todo.data.entity.TodoData
import org.michaelbel.todo.databinding.CardItemBinding
import java.text.SimpleDateFormat
import java.util.*

class TodoViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(it: TodoData) {
        binding.titleTextView.text = it.title
        binding.descriptionTextView.text = it.description
        binding.timeTextView.text = timeString(it.time)
        binding.root.strokeColor = priorityColor(it.priority)
    }

    private fun timeString(it: Long): String {
        val simpleDateFormat = SimpleDateFormat(itemView.context.getString(R.string.todo_date_format), Locale.getDefault())
        return simpleDateFormat.format(Date(it))
    }

    private fun priorityColor(it: Priority): Int {
        return ContextCompat.getColor(itemView.context, when (it) {
            Priority.LOW -> R.color.color_priority_low
            Priority.MEDIUM -> R.color.color_priority_medium
            Priority.HIGH -> R.color.color_priority_high
        })
    }
}