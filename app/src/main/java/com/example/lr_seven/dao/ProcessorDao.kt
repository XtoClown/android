package com.example.lr_seven.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lr_seven.entity.Processor
import com.example.lr_seven.entity.ProcessorAndSocketNameTuple

@Dao
interface  ProcessorDao {
    @Query("SELECT * FROM Processor")
    suspend fun getAll(): List<Processor>

    @Insert
    suspend fun insert(vararg processors: Processor)

    @Query("SELECT * FROM Processor WHERE processorName == :name LIMIT 1")
    suspend fun readByName(name: String): Processor

    @Query("SELECT * FROM Processor WHERE processorSocketName == :name LIMIT 1")
    suspend fun readBySocketName(name: String): Processor

    @Query("SELECT processorName, processorSocketName FROM Processor")
    suspend fun getProcessorAndSocketNames(): List<ProcessorAndSocketNameTuple>

    @Update
    suspend fun update(vararg processors: Processor)

    @Delete
    suspend fun delete(processor: Processor)

    @Query("DELETE from Processor")
    suspend fun deleteAll()
}