package com.example.lr_seven

import android.app.Application
import androidx.room.Room.databaseBuilder
import com.example.lr_seven.db.AppDatabase

class App: Application() {

    var database: AppDatabase? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = databaseBuilder(this, AppDatabase::class.java, "database").build()
    }

    companion object {
        var instance: App? = null
    }
}