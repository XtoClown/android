package com.example.lr_seven.entity

import androidx.room.Embedded
import androidx.room.Relation

data class SocketProcessors(
    @Embedded val socket: Socket,
    @Relation(
        parentColumn = "socketName",
        entityColumn = "processorSocketName"
    )
    val processors: List<Processor>
)