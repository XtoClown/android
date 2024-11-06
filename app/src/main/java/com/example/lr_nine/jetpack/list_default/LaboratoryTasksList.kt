package com.example.lr_nine.jetpack.list_default

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun LaboratoryTasksList(
    list: List<LaboratoryTask>,
    checkedCount: MutableState<Int>,
    onCloseTask: (LaboratoryTask) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn ( modifier = modifier ){
        items(
            items = list,
            key = { task -> task.id }
        ){ task ->
            LaboratoryTaskItem(taskName = task.label, checkedCount = checkedCount, description = task.description, onClose = { onCloseTask(task) })
        }
    }
}