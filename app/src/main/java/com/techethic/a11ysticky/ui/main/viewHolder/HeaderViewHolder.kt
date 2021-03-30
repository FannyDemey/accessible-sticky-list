package com.techethic.a11ysticky.ui.main.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.R
import com.techethic.a11ysticky.databinding.ItemStickyBinding

class HeaderViewHolder(private val itemBinding: ItemStickyBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(rating: Int) = with(itemBinding) {
        stickyRateText.text = rating.toString()
        stickyRateText.contentDescription = String.format(
                stickyRateText.resources.getString(R.string.the_following_items_have_a_rate_of),
                rating
        )
    }
}