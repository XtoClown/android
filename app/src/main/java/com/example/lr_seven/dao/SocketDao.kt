package com.example.lr_seven.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lr_seven.entity.Processor
import com.example.lr_seven.entity.Socket

@Dao
interface SocketDao {
    @Query("SELECT * FROM Socket")
    fun getAll(): List<Socket>

    @Insert
    fun insert(vararg sockets: Socket)

    @Query("SELECT * FROM Socket WHERE socketName == :name LIMIT 1")
    fun readByName(name: String): Socket

    @Query("SELECT * FROM Socket WHERE companyName == :name LIMIT 1")
    fun readByCompanyName(name: String): Socket

    @Query("SELECT * FROM Socket" + " JOIN Processor ON Socket.socketName == Processor.processorSocketName")
    fun getSocketAndProcessorsOnHim(): Map<Socket, List<Processor>>

    @Update
    fun update(vararg sockets: Socket)

    @Query("DELETE FROM Socket WHERE socketName == :socketName")
    fun delete(socketName: String)

    @Query("DELETE from Socket")
    fun deleteAll()
}