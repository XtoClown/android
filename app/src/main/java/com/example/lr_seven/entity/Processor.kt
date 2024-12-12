package com.example.lr_seven.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Processor(
    @PrimaryKey(autoGenerate = true) var pid: Int,
    @ColumnInfo(name = "processorName") var processorName: String?,
    @ColumnInfo(name = "processorSocketName") var processorSocketName: String?
)