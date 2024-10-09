package com.example.lr6

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var games: ArrayList<Game> = ArrayList<Game>()
    var gamesList: ListView? = null
    var selectedGames: ArrayList<Game>? = ArrayList<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        games.add(Game("Elden Ring", 2022, R.drawable.elden_ring))
        games.add(Game("Baldurs Gate III", 2023, R.drawable.baldus_gate3))

        gamesList = findViewById(R.id.listView)

        val gameAdapter = GameAdapter(this, R.layout.list_item, games)

        gamesList?.adapter = gameAdapter

        gamesList?.setOnItemClickListener(OnItemClickListener { parent, v, position, id ->
            val game = gameAdapter!!.getItem(position)
            if(gamesList!!.isItemChecked(position)){
                v.setBackgroundColor(Color.parseColor("#257349"))
                selectedGames?.add(game!!)
            }
            else {
                v.setBackgroundColor(Color.parseColor("#ffffff"))
                selectedGames?.remove(game)
            }
        })


        var gamesSpinnerArray: Array<String> = arrayOf("Elden Ring", "Baldur's Gate III",
            "Wukong", "Witcher 3", "GTA V")

        val spinner = findViewById<Spinner>(R.id.spinner)
        val spinnerAdapter = ArrayAdapter<String?>(this,
            android.R.layout.simple_spinner_item, gamesSpinnerArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            add(gameAdapter)
        }

        val removeButtton = findViewById<Button>(R.id.removeButton)
        removeButtton.setOnClickListener {
            remove(gameAdapter)
        }

        val activityTwoButton = findViewById<Button>(R.id.activityTwoButton)
        activityTwoButton.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }

    fun add(adapter: GameAdapter){
        val gameName = findViewById<EditText>(R.id.gameNameEditText)
        val gameYear = findViewById<EditText>(R.id.gameYearEditText)

        val name = gameName.text.toString()
        val year = gameYear.text.toString()

        try{
            if(!name.isEmpty() && !year.isEmpty()){
                adapter!!.add(Game(name, year.toInt(), R.drawable.picture))
                gameName.setText("")
                gameYear.setText("")
                adapter!!.notifyDataSetChanged()
            }
        }
        catch (ex: Exception){
            gameName.setText("")
            gameYear.setText("")
        }
    }

    fun remove(adapter: GameAdapter){
        for(item in selectedGames!!.indices){
            adapter!!.remove(selectedGames!![item])
        }

        gamesList!!.clearChoices()
        selectedGames!!.clear()
        adapter!!.notifyDataSetChanged()
    }

}

