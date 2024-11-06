package com.example.lr_nine

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lr_nine.jetpack.list_default.LaboratoryTaskScreen

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                LaboratoryTaskScreen(context = this)
            }
        }
    }
}