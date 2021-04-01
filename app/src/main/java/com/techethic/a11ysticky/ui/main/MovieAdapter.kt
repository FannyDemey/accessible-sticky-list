package com.techethic.a11ysticky.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.databinding.ItemMovieBinding
import com.techethic.a11ysticky.databinding.ItemStickyBinding
import com.techethic.a11ysticky.model.Movie
import com.techethic.a11ysticky.ui.main.decoration.StickyHeaderDecoration
import com.techethic.a11ysticky.ui.main.viewHolder.HeaderViewHolder
import com.techethic.a11ysticky.ui.main.viewHolder.MovieViewHolder


class MovieAdapter(
        private val data: List<Movie>
) : RecyclerView.Adapter<MovieViewHolder>(),
    StickyHeaderDecoration.Adapter<HeaderViewHolder> {

    private val attachedViewIds = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Log.d("Fanny", "movie position onBindMovieViewHolder $position")
        holder.bind(data[position])
        attachedViewIds.add(holder.itemView.id)

    }


    override fun getItemCount(): Int = data.size
    override fun getHeaderId(adapterPos: Int): Long  = getMovieRate(adapterPos).toLong()

    override fun onBindHeaderViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(getMovieRate(position))
        attachedViewIds.add(holder.itemView.id)
    }

    override fun onCreateHeaderViewHolder(parent: RecyclerView) =
        HeaderViewHolder(ItemStickyBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    private fun getMovieRate(adapterPosition: Int) = data[adapterPosition].rate

    //Allow last item to be scrolled to the top of the sticky list
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.clipToPadding = false
        recyclerView.doOnNextLayout { recyclerView.updatePadding(bottom = recyclerView.height)}
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
        val viewId = holder.itemView.id
        attachedViewIds.remove(viewId)
    }

    override fun viewHasBeenRecycled(viewId : Int) : Boolean {
        return attachedViewIds.contains(viewId)
    }

}