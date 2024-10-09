package com.example.lr6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameAdapterRecycler internal constructor(context: Context?, games: List<Game>) :
    RecyclerView.Adapter<GameAdapterRecycler.ViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val games: List<Game> = games

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameAdapterRecycler.ViewHolder {
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game: Game = games[position]
        holder.gameImageView.setImageResource(game.gameImage)
        holder.gameNameView.text = game.gameName
        holder.gameYearView.text = game.gameYear.toString()
    }

    override fun getItemCount(): Int {
        return games.size
    }

    class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view){
        val gameImageView: ImageView = view.findViewById(R.id.gameImageView)
        val gameNameView: TextView = view.findViewById(R.id.gameNameView)
        val gameYearView: TextView = view.findViewById(R.id.gameYearView)
    }

}