package com.example.desafiofirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.desafiofirebase.databinding.ItemGameListBinding
import com.example.desafiofirebase.helper.OnGameClickListener
import com.example.desafiofirebase.provider.database.entities.Game
import javax.inject.Inject

class GamesListAdapter (
    private val glide: RequestManager,
    private val itemClickListener: OnGameClickListener
) : RecyclerView.Adapter<GamesListAdapter.GamesView>() {

    var listGames: List<Game> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesView {
        val view = ItemGameListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return GamesView(view)
    }

    override fun onBindViewHolder(holder: GamesView, position: Int) {
        holder.bind(listGames[position])
    }

    override fun getItemCount(): Int = listGames.size

    inner class GamesView(private val viewBinding: ItemGameListBinding) :
        RecyclerView.ViewHolder(viewBinding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(game: Game) {
            viewBinding.txtNameGame.text = game.name
            viewBinding.txtYearGame.text = game.createdYear.toString()
            glide.load(game.imageUrl).into(viewBinding.imGame)
        }

        override fun onClick(v: View?) {
            val game = listGames.elementAt(adapterPosition)
            itemClickListener.onGameClick(game.uuid)
        }
    }
}