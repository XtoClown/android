package com.example.lr_seven.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Socket(
    @PrimaryKey @NonNull var socketName: String,
    @ColumnInfo(name = "companyName") var companyName: String?
)