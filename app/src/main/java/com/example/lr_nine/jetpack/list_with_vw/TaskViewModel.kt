package com.example.lr_nine.jetpack.list_with_vw

import androidx.lifecycle.ViewModel

class TaskViewModel: ViewModel() {
    private val _tasks = getTasks().toMutableList()
    val tasks: List<Task>
        get() = _tasks

    fun remove(item: Task){
        _tasks.remove(item)
    }

    fun changeTaskChecked(item: Task, checked: Boolean) =
        _tasks.find { it.id == item.id }?.let { task ->
            task.checked.value = checked
        }
}

private fun getTasks() = List(30){ i -> Task(i, "Task # $i") }