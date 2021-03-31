package com.techethic.a11ysticky.ui.main.viewHolder

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.R
import com.techethic.a11ysticky.databinding.ItemStickyBinding

class HeaderViewHolder(private val itemBinding: ItemStickyBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    fun bind(rating: Int) = with(itemBinding) {
        stickyRateText.text = rating.toString()
        stickyRateText.contentDescription = String.format(
                stickyRateText.resources.getString(R.string.the_following_items_have_a_rate_of),
                rating
        )
        if(rating == 3){
            Log.d("Fanny","before ${itemView.accessibilityTraversalBefore} - id : ${itemView.id} - after ${itemView.accessibilityTraversalAfter}")
        }



    }
}