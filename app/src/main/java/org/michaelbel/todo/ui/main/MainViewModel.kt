package org.michaelbel.todo.ui.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import org.michaelbel.todo.R
import org.michaelbel.todo.data.entity.TodoData
import org.michaelbel.todo.domain.DeleteTodoUseCase
import org.michaelbel.todo.domain.GetTodoListUseCase
import org.michaelbel.todo.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTodoListUseCase: GetTodoListUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
): BaseViewModel() {

    val getTodoList: Flowable<PagingData<TodoData>>
        get() {
            return getTodoListUseCase.getTodoList().cachedIn(viewModelScope)
        }

    fun deleteTodo(it: TodoData) {
        deleteTodoUseCase.deleteTodo(it)
            .subscribe({ toastEvent.onNext(R.string.message_removed) }, { Timber.e(it) })
            .autoDispose()
    }
}