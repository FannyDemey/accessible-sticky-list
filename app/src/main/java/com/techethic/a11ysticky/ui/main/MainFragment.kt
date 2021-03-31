package com.techethic.a11ysticky.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techethic.a11ysticky.R
import com.techethic.a11ysticky.databinding.MainFragmentBinding
import com.techethic.a11ysticky.ui.main.decoration.StickyHeaderDecoration

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding = MainFragmentBinding.bind(view)

        val movieAdapter = MovieAdapter(viewModel.movies)
        binding.list.apply {
            adapter = movieAdapter
            addItemDecoration(StickyHeaderDecoration(movieAdapter, binding.stickyHeader))
        }
    }
}