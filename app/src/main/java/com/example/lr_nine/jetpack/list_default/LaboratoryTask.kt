package com.example.lr_nine.jetpack.list_default

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

data class LaboratoryTask (val id: Int,
                           val label: String,
                           val description: String,
                           val checked: Boolean = false)


val laboratoryTaskSaver = listSaver<LaboratoryTask, Any>(
    save = {
        listOf<Any>(it.id, it.label, it.description, it.checked)
    },
    restore = { data ->
        LaboratoryTask(
            id = data[0] as Int,
            label = data[1] as String,
            description = data[2] as String,
            checked = data[3] as Boolean)
    }
)

val laboratoryTasksSaver = listSaver<MutableList<LaboratoryTask>, Any>(
    save = {
        val list = it
        val result = mutableListOf<Any>()

        for(item in list){
            result.add(listOf<Any>(item.id, item.label, item.description, item.checked))
        }

        result
    },
    restore = { data ->
        val list: List<Any> = data
        val result = mutableListOf<LaboratoryTask>()

        for(item in list){
            var obj = item as List<Any>
            result.add(LaboratoryTask(
                id = obj[0] as Int,
                label = obj[1] as String,
                description = obj[2] as String,
                checked = obj[3] as Boolean))
        }

        result
    }
)

