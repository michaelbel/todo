package org.michaelbel.todo.data.converter

import androidx.room.TypeConverter
import org.michaelbel.todo.data.entity.Priority

class Converter {

    @TypeConverter
    fun fromPriority(it: Priority): String {
        return it.name
    }

    @TypeConverter
    fun toPriority(it: String): Priority {
        return Priority.valueOf(it)
    }
}