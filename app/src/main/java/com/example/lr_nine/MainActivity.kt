package com.example.lr_nine

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import com.example.lr_nine.jetpack.list_default.LaboratoryTaskScreen
import com.example.lr_nine.jetpack.list_with_vw.TaskScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                TaskScreen(context = this)
            }
        }
    }
}