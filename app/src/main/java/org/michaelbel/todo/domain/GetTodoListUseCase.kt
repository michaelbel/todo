package org.michaelbel.todo.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import io.reactivex.Flowable
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.entity.TodoData
import javax.inject.Inject

class GetTodoListUseCase @Inject constructor(
    private val repository: Contract.TodoRepository
) {

    fun getTodoList(): Flowable<PagingData<TodoData>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { repository.getTodoList() }
        ).flowable
    }
}