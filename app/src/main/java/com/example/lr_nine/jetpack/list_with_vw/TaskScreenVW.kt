package com.example.lr_nine.jetpack.list_with_vw

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lr_nine.MainActivity2

@Composable
fun TaskScreen(modifier: Modifier = Modifier, taskViewModel: TaskViewModel = viewModel(), context: Activity){
    Column (modifier = modifier){
        Text(
            modifier = Modifier.height(60.dp).fillMaxWidth(),
            text = "Tasks done: ${taskViewModel.checkedCount.value}",
            textAlign = TextAlign.Center,
            fontSize = 50.sp
        )
        TasksList(
            list = taskViewModel.tasks,
            onCheckedTask = { task, checked ->
                taskViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                taskViewModel.remove(task)
            }
        )
        Button(onClick = {
            val intent = Intent(context, MainActivity2::class.java)
            context.startActivity(intent) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)) {
            Text(text = "List without ViewModel", fontSize=30.sp)
        }
    }
}
