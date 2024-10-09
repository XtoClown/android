package com.example.lr6

import android.widget.ArrayAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class GameAdapter (context: Context?, resource: Int, private val games: List<Game>) :
    ArrayAdapter<Game?>(context!!, resource, games){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private val layout = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(this.layout, parent, false)

        val gameImageView = view.findViewById<ImageView>(R.id.gameImageView)
        val gameNameView = view.findViewById<TextView>(R.id.gameNameView)
        val gameYearView = view.findViewById<TextView>(R.id.gameYearView)

        val game: Game = games[position]

        gameImageView.setImageResource(game.gameImage)
        gameNameView.text = game.gameName
        gameYearView.text = game.gameYear.toString()

        return view
    }
}