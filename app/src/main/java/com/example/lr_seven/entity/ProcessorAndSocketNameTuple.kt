package com.example.lr_seven.entity

import androidx.room.ColumnInfo

data class ProcessorAndSocketNameTuple(
    @ColumnInfo(name = "processorName") val processorName: String?,
    @ColumnInfo(name = "processorSocketName") val processorSocketName: String?
)