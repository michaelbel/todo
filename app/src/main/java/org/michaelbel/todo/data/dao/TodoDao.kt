package org.michaelbel.todo.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import io.reactivex.Completable
import org.michaelbel.todo.data.entity.TodoData

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY id ASC")
    fun getTodoList(): PagingSource<Int, TodoData>

    @Delete
    fun deleteTodo(it: TodoData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTodo(it: TodoData): Completable

    @Update
    fun updateTodo(it: TodoData): Completable
}