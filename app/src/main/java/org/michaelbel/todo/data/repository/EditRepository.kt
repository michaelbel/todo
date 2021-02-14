package org.michaelbel.todo.data.repository

import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.dao.TodoDao
import org.michaelbel.todo.data.entity.TodoData
import javax.inject.Inject

class EditRepository @Inject constructor(
    private val todoDao: TodoDao
): Contract.EditRepository {

    override fun insertTodo(it: TodoData): Completable {
        return todoDao.insertTodo(it).subscribeOn(Schedulers.io())
    }

    override fun updateTodo(it: TodoData): Completable {
        return todoDao.updateTodo(it).subscribeOn(Schedulers.io())
    }
}