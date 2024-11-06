package com.example.lr_nine.jetpack.list_with_vw

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.time.delay

@Composable
fun TaskItem(
    taskName: String,
    description: String,
    checked: MutableState<Boolean>,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit
){
    var close by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(close) {
        if(close){
            delay(500)
            onClose()
        }
    }

    AnimatedVisibility(
       visible = !close,
        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Row(modifier = modifier.weight(0.5f), verticalAlignment = Alignment.CenterVertically){
                TopicRow(
                    topic = taskName,
                    description = description,
                    expanded = expanded,
                    onClick = onClick
                )
            }
            Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically){
                Checkbox(
                    checked = checked.value,
                    onCheckedChange = onCheckedChange
                )
                IconButton(onClick = {
                    close = true
                }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            }
        }
    }
}