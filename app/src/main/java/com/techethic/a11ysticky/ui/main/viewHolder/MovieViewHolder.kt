package com.techethic.a11ysticky.ui.main.viewHolder

import com.techethic.a11ysticky.databinding.ItemMovieBinding
import com.techethic.a11ysticky.model.MovieListItem

class MovieViewHolder(private val itemBinding: ItemMovieBinding) : BaseViewHolder<MovieListItem.Movie>(itemBinding ) {

    override fun bind(movie: MovieListItem.Movie){
        itemBinding.apply {
            movieTitle.text = movie.title
            movieRate.text = movie.rate.toString()
        }

    }
}