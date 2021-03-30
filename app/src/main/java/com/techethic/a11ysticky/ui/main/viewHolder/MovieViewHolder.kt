package com.techethic.a11ysticky.ui.main.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.databinding.ItemMovieBinding
import com.techethic.a11ysticky.model.Movie

class MovieViewHolder(private val itemBinding: ItemMovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(movie: Movie){
        itemBinding.apply {
            movieTitle.text = movie.title
            movieRate.text = movie.rate.toString()
        }

    }
}