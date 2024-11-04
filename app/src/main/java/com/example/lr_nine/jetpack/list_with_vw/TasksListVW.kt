package com.example.lr_nine.jetpack.list_with_vw

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun TasksList(
    list: List<Task>,
    onCheckedTask: (Task, Boolean) -> Unit,
    onCloseTask: (Task) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn ( modifier = modifier ){
        items(
            items = list,
            key = { task -> task.id }
        ){ task ->
            var expanded by rememberSaveable { mutableStateOf(false) }
            TaskItem(
                taskName = task.label,
                checked = task.checked,
                onCheckedChange = { checked -> onCheckedTask(task, checked) },
                onClose = { onCloseTask(task) },
                expanded = expanded,
                onClick = { expanded = !expanded }
                )
        }
    }
}