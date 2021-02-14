package org.michaelbel.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.michaelbel.todo.data.converter.Converter
import org.michaelbel.todo.data.dao.TodoDao
import org.michaelbel.todo.data.entity.TodoData

@Database(entities = [TodoData::class], exportSchema = false, version = 1)
@TypeConverters(Converter::class)
abstract class TodoDatabase: RoomDatabase() {

    abstract fun todoDao(): TodoDao

    companion object {
        private const val DATABASE_NAME = "todo_database"

        @Volatile
        private var instance: TodoDatabase? = null

        fun instance(context: Context): TodoDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): TodoDatabase {
            return Room.databaseBuilder(context, TodoDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}