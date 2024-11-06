package com.example.lr_nine.jetpack.list_with_vw

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class Task(
    val id: Int,
    val label: String,
    val description: String,
    initialChecked: Boolean = false){
    var checked: MutableState<Boolean> = mutableStateOf(initialChecked)
}
