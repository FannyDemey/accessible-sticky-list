package com.techethic.a11ysticky.ui.main.viewHolder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.techethic.a11ysticky.model.MovieListItem

abstract class BaseViewHolder<T : MovieListItem>(viewBinding: ViewBinding
) : RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bind(eventItem: T)
}