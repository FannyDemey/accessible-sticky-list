package com.techethic.a11ysticky.ui.main.decoration


import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.collection.LongSparseArray
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView


class StickyHeaderDecoration<T : RecyclerView.ViewHolder>(
        private val adapter: Adapter<T>,
        private val frameLayout: FrameLayout,
) : RecyclerView.ItemDecoration() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val attachedViews = LongSparseArray<T>()
    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val headerHeight = if (position != RecyclerView.NO_POSITION && hasHeader(position)) {
            getHeader(parent, position).itemView.height
        } else 0
        outRect[0, headerHeight, 0] = 0
    }
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.childCount
        val newlyAttached = mutableListOf<View>()
        for (layoutPos in 0 until count) {
            val child: View = parent.getChildAt(layoutPos)
            val adapterPos: Int = parent.getChildAdapterPosition(child)
            if (adapterPos != RecyclerView.NO_POSITION && (layoutPos == 0 || hasHeader(adapterPos))) {
                val holder = getHeader(parent, adapterPos)
                adapter.onBindHeaderViewHolder(holder, adapterPos)
                val header = holder.itemView
                val left = child.left
                val top = getHeaderTop(parent, child, header, adapterPos, layoutPos)
                if (header.parent == null) {
                    frameLayout.addView(header)
                }
                header.translationY = top.toFloat()
                header.translationX = left.toFloat()
                newlyAttached.add(header)
                attachedViews.append(adapter.getHeaderId(adapterPos), holder)
            }
        }
        frameLayout.children.forEach {
            if (it !is RecyclerView && it !in newlyAttached) {
                val holder = it.tag as T
                attachedViews.removeAt(attachedViews.indexOfValue(holder))
                viewPool.putRecycledView(holder)
                frameLayout.removeView(it)
            }
        }
    }
    private fun hasHeader(position: Int): Boolean {
        val headerId = adapter.getHeaderId(position)
        if (position == 0) {
            return headerId != RecyclerView.NO_ID
        }
        return headerId != RecyclerView.NO_ID && (headerId != adapter.getHeaderId(position - 1))
    }
    private fun getHeader(parent: RecyclerView, position: Int): T {
        val recycled = attachedViews.get(adapter.getHeaderId(position), viewPool.getRecycledView(0) as? T)
        if (recycled != null) {
            return recycled
        }
        val holder = adapter.onCreateHeaderViewHolder(parent)
        val header: View = holder.itemView
        val widthSpec =
                View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec =
                View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
        val childWidth = ViewGroup.getChildMeasureSpec(
                widthSpec,
                parent.paddingLeft + parent.paddingRight, header.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
                heightSpec, parent.paddingTop + parent.paddingBottom, header.layoutParams.height
        )
        header.measure(childWidth, childHeight)
        header.layout(0, 0, header.measuredWidth, header.measuredHeight)
        header.tag = holder
        return holder
    }
    private fun getHeaderTop(
            parent: RecyclerView,
            child: View,
            header: View,
            adapterPos: Int,
            layoutPos: Int
    ): Int {
        val headerHeight = header.height
        val top = child.top - headerHeight
        if (layoutPos != 0) {
            return top
        }
        // find next view with header and compute the offscreen push if needed
        for (i in 1 until parent.childCount) {
            val adapterPosHere = parent.getChildAdapterPosition(parent.getChildAt(i))
            if (adapterPosHere == RecyclerView.NO_POSITION) {
                continue
            }
            val nextId: Long = adapter.getHeaderId(adapterPosHere)
            if (nextId == adapter.getHeaderId(adapterPos)) {
                continue
            }
            val next: View = parent.getChildAt(i)
            val offset: Int = next.top - (headerHeight + (getHeader(
                    parent,
                    adapterPosHere
            ).itemView.height))
            if (offset < 0) {
                return offset
            }
        }
        return top.coerceAtLeast(0)
    }
    interface Adapter<T : RecyclerView.ViewHolder> {
        fun getItemCount(): Int
        fun getHeaderId(adapterPos: Int): Long
        fun onBindHeaderViewHolder(holder: T, position: Int)
        fun onCreateHeaderViewHolder(parent: RecyclerView): T
    }
}