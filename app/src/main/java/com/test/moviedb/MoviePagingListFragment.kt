package com.test.moviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.moviedb.Constants.FRAGMENT_MOVIE_DETAIL
import com.test.moviedb.adapter.MoviePagingAdapter
import com.test.moviedb.adapter.MoviesLoadStateAdapter
import com.test.moviedb.listeners.ItemClickListener
import com.test.moviedb.room.model.MovieTable
import com.test.moviedb.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MoviePagingListFragment : BaseFragment(), ItemClickListener {

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val moviePagingAdapter = MoviePagingAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = moviePagingAdapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { moviePagingAdapter.retry() },
            footer = MoviesLoadStateAdapter { moviePagingAdapter.retry() }
        )

        lifecycleScope.launch {
            viewModel.passengers.collectLatest { pagedData ->
                moviePagingAdapter.submitData(pagedData)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MoviePagingListFragment().apply {
            }
    }


    override fun <T : Any> onItemClick(obj: T) {
        listener.replaceFragment(
            MovieDetailFragment.newInstance(obj as MovieTable),
            FRAGMENT_MOVIE_DETAIL,
            true
        )
    }
}