package com.example.lr_seven.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.lr_seven.entity.SocketProcessors

@Dao
interface SocketProcessorDao {
    @Transaction
    @Query("SELECT * FROM Socket")
    fun getAll(): List<SocketProcessors>

    @Transaction
    @Query("SELECT * FROM Socket where socketName == :name")
    fun readBySocketName(name: String): List<SocketProcessors>
}