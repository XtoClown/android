package com.example.lr6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity2 : AppCompatActivity() {

    private var games: ArrayList<Game> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        games.add(Game("Elden Ring", 2022, R.drawable.elden_ring))
        games.add(Game("Baldurs Gate III", 2023, R.drawable.baldus_gate3))

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = GameAdapterRecycler(this, games)

        recyclerView.adapter = adapter

        val activityOneButton = findViewById<Button>(R.id.activityOneButton)
        activityOneButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}