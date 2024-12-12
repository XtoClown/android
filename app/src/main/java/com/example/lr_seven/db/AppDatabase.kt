package com.example.lr_seven.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lr_seven.dao.ProcessorDao
import com.example.lr_seven.dao.SocketDao
import com.example.lr_seven.dao.SocketProcessorDao
import com.example.lr_seven.entity.Socket
import com.example.lr_seven.entity.Processor

@Database(entities = [Socket::class, Processor::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun socketDao(): SocketDao
    abstract fun processorDao(): ProcessorDao
    abstract fun socketProcessorsDao(): SocketProcessorDao
}