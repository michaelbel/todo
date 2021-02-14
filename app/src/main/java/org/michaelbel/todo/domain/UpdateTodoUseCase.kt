package org.michaelbel.todo.domain

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.entity.TodoData
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val repository: Contract.EditRepository
) {

    fun updateTodo(it: TodoData): Completable {
        return repository.updateTodo(it).observeOn(AndroidSchedulers.mainThread())
    }
}