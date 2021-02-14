package org.michaelbel.todo.data.repository

import androidx.paging.PagingSource
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.dao.TodoDao
import org.michaelbel.todo.data.entity.TodoData
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
): Contract.TodoRepository {

    override fun getTodoList(): PagingSource<Int, TodoData> {
        return todoDao.getTodoList()
    }

    override fun deleteTodo(it: TodoData): Completable {
        return todoDao.deleteTodo(it).subscribeOn(Schedulers.io())
    }
}