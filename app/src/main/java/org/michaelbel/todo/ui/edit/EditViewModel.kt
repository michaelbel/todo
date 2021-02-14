package org.michaelbel.todo.ui.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import org.michaelbel.todo.R
import org.michaelbel.todo.data.entity.TodoData
import org.michaelbel.todo.domain.InsertTodoUseCase
import org.michaelbel.todo.domain.UpdateTodoUseCase
import org.michaelbel.todo.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val insertTodoUseCase: InsertTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
): BaseViewModel() {

    fun insertTodo(it: TodoData) {
        insertTodoUseCase.insertTodo(it)
            .subscribe({ toastEvent.onNext(R.string.message_inserted) }, { Timber.e(it) })
            .autoDispose()
    }

    fun updateTodo(it: TodoData) {
        updateTodoUseCase.updateTodo(it)
            .subscribe({ toastEvent.onNext(R.string.message_updated) }, { Timber.e(it) })
            .autoDispose()
    }
}