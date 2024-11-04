package com.example.lr_nine.jetpack.list_with_vw

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TaskScreen(modifier: Modifier = Modifier, taskViewModel: TaskViewModel = viewModel()){
    Column (modifier = modifier){
        TasksList(
            list = taskViewModel.tasks,
            onCheckedTask = { task, checked ->
                taskViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                taskViewModel.remove(task)
            }
        )
    }
}
