package com.awais.cleanarchitecturesolid.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cleanarchitecturesolid.R

@Database(entities = [NoteEntity::class], version = 1)
abstract class DatabaseService : RoomDatabase() {
    companion object {
        private var instance: DatabaseService? = null
        private fun create(context: Context): DatabaseService =
            Room.databaseBuilder(
                context, DatabaseService::class.java, context.getString(R.string.database_name)
            ).fallbackToDestructiveMigration().build()

        fun getInstance(context: Context): DatabaseService =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun noteDao(): NoteDao
}