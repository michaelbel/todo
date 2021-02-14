package org.michaelbel.todo.data

import androidx.paging.PagingSource
import io.reactivex.Completable
import org.michaelbel.todo.data.entity.TodoData

interface Contract {

    interface EditRepository {
        fun insertTodo(it: TodoData): Completable
        fun updateTodo(it: TodoData): Completable
    }

    interface TodoRepository {
        fun getTodoList(): PagingSource<Int, TodoData>
        fun deleteTodo(it: TodoData): Completable
    }
}