package com.example.koinmvvm.database.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(objectData: T): Long

    @Update
    fun update(objectData: T): Int

    @Delete
    fun delete(objectData: T): Int
}