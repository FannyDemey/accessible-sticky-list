package com.techethic.a11ysticky.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.R
import com.techethic.a11ysticky.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.list.apply {
            val screenHeight = resources.displayMetrics.heightPixels - binding.stickyHeader.minimumHeight
            adapter = MovieAdapter(viewModel.movies, screenHeight)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    checkFirstVisibleItemAndUpdateSticky()
                }
            })
        }
    }

    private fun RecyclerView.checkFirstVisibleItemAndUpdateSticky() {
        (adapter as? MovieAdapter)?.let { movieAdapter ->
            val firstPosition = (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
            if (firstPosition != null) {
                val stickyHeaderRate = movieAdapter.getMovieRate(firstPosition)
                updateStickyHeaderView(stickyHeaderRate)
            }
        }

    }

    private fun updateStickyHeaderView(rate : Int) {
        val view = layoutInflater.inflate(R.layout.item_sticky, null)
        view.findViewById<TextView>(R.id.stickyRateText)?.apply {
            text = rate.toString()
            contentDescription = String.format(resources.getString(R.string.the_following_items_have_a_rate_of), rate)
        }
        binding.stickyHeader.removeAllViews()
        binding.stickyHeader.addView(view)
    }

}