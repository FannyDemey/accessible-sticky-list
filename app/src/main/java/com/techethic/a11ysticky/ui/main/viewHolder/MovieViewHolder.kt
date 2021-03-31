package com.techethic.a11ysticky.ui.main.viewHolder

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.databinding.ItemMovieBinding
import com.techethic.a11ysticky.model.Movie

class MovieViewHolder(private val itemBinding: ItemMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(movie: Movie){
        itemBinding.apply {
            movieItemLayout.id = ViewCompat.generateViewId()
            movieTitle.text = movie.title
            movieRate.text = movie.rate.toString()
        }
        itemView.setOnClickListener {
            Log.d("Fanny","touch item ${itemView.id}")
        }
        Log.d("Fanny","Movie  ${itemView.id} $movie")

    }
}