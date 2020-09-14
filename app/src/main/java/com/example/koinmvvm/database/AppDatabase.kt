package com.example.koinmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.koinmvvm.database.constants.DATABASE_NAME
import com.example.koinmvvm.database.constants.DATABASE_VERSION
import com.example.koinmvvm.database.daos.articles.ArticlesDao
import com.example.koinmvvm.database.entities.articles.ArticlesEntity

@Database(
    entities = [
        ArticlesEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
@TypeConverters(DateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticlesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_TO_2)
                .allowMainThreadQueries()
                .build()
        }
    }
}