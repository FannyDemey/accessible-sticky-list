package com.techethic.a11ysticky.ui.main.viewHolder

import com.techethic.a11ysticky.R
import com.techethic.a11ysticky.databinding.ItemStickyBinding
import com.techethic.a11ysticky.model.MovieListItem

class StickyHeaderViewHolder(private val itemBinding: ItemStickyBinding) : BaseViewHolder<MovieListItem.StickyHeader>(itemBinding ) {

    override fun bind(stickyHeader: MovieListItem.StickyHeader){
        itemBinding.apply {
            stickyRateText.text = stickyHeader.rate.toString()
            stickyRateText.contentDescription = String.format(
                stickyRateText.resources.getString(R.string.the_following_items_have_a_rate_of),
                stickyHeader.rate)
        }

    }
}