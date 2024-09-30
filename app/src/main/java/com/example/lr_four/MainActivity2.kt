package com.example.lr_four

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val img1: ImageView = findViewById(R.id.imageView1)
        val img2: ImageView = findViewById(R.id.imageView2)

        img1.setOnClickListener{
            img2.visibility = View.VISIBLE
            img1.visibility = View.GONE
        }
        img2.setOnClickListener{
            img1.visibility = View.VISIBLE
            img2.visibility = View.GONE
        }
    }
}