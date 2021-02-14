package org.michaelbel.todo.di

import android.content.Context
import android.os.Vibrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.michaelbel.todo.data.Contract
import org.michaelbel.todo.data.TodoDatabase
import org.michaelbel.todo.data.dao.TodoDao
import org.michaelbel.todo.data.repository.EditRepository
import org.michaelbel.todo.data.repository.TodoRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideVibratorService(@ApplicationContext context: Context): Vibrator {
        return context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): TodoDatabase {
        return TodoDatabase.instance(context)
    }

    @Provides
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.todoDao()
    }

    @Provides
    fun provideTodoRepository(todoDao: TodoDao): Contract.TodoRepository {
        return TodoRepository(todoDao)
    }

    @Provides
    fun provideEditRepository(todoDao: TodoDao): Contract.EditRepository {
        return EditRepository(todoDao)
    }
}