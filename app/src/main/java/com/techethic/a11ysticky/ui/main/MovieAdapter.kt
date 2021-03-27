package com.techethic.a11ysticky.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.databinding.ItemMovieBinding
import com.techethic.a11ysticky.databinding.ItemStickyBinding
import com.techethic.a11ysticky.model.MovieListItem
import com.techethic.a11ysticky.ui.main.viewHolder.BaseViewHolder
import com.techethic.a11ysticky.ui.main.viewHolder.MovieViewHolder
import com.techethic.a11ysticky.ui.main.viewHolder.StickyHeaderViewHolder
import java.lang.IllegalStateException


class MovieAdapter(data: List<MovieListItem.Movie>, private val lastItemPaddingBottom : Int) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var moviesWithHeader =  emptyList<MovieListItem>()

    init {
        val stickyHeaders = data.distinctBy { it.rate }
                                .map { MovieListItem.StickyHeader(it.rate) }

        moviesWithHeader = data.plus(stickyHeaders).sorted()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType){
            CELL_MOVIE -> {
                val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(itemBinding)
            }
            CELL_STICKY -> {
                val itemBinding = ItemStickyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                StickyHeaderViewHolder(itemBinding)
            }
            else -> throw IllegalStateException("wrong cell type in movie recyclerview")
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(moviesWithHeader[position]){
            is MovieListItem.Movie -> CELL_MOVIE
            is MovieListItem.StickyHeader -> CELL_STICKY
        }
    }
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        handleLastItemMarginBottom(position, holder)
        when(holder){
            is MovieViewHolder -> holder.bind(moviesWithHeader[position] as MovieListItem.Movie)
            is StickyHeaderViewHolder -> holder.bind(moviesWithHeader[position] as MovieListItem.StickyHeader)
        }
    }

    //Allow last item to be scrolled to the top of the sticky list
    private fun handleLastItemMarginBottom(
        position: Int,
        holder: BaseViewHolder<*>
    ) {
        if (position == moviesWithHeader.lastIndex) {
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = lastItemPaddingBottom
            holder.itemView.layoutParams = params
        } else {
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = 0
            holder.itemView.layoutParams = params
        }
    }

    override fun getItemCount(): Int = moviesWithHeader.size


    fun getMovieRate(adapterPosition : Int) : Int {
        return moviesWithHeader[adapterPosition].rate
    }

    companion object {
        const val CELL_MOVIE = 0
        const val CELL_STICKY = 1
    }
}