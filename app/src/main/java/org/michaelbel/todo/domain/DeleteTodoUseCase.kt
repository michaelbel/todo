package org.michaelbel.todo.domain

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.entity.TodoData
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val repository: Contract.TodoRepository
) {

    fun deleteTodo(it: TodoData): Completable {
        return repository.deleteTodo(it).observeOn(AndroidSchedulers.mainThread())
    }
}