package org.michaelbel.todo.domain

import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.entity.TodoData
import javax.inject.Inject

class InsertTodoUseCase @Inject constructor(
    private val repository: Contract.EditRepository
) {

    fun insertTodo(it: TodoData): Completable {
        return repository.insertTodo(it).observeOn(AndroidSchedulers.mainThread())
    }
}