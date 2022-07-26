package com.test.moviedb

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.moviedb.Constants.FRAGMENT_MOVIE_DETAIL
import com.test.moviedb.adapter.MoviePagingAdapter
import com.test.moviedb.adapter.MoviesLoadStateAdapter
import com.test.moviedb.listeners.ItemClickListener
import com.test.moviedb.room.DbDao
import com.test.moviedb.room.RoomDataBase
import com.test.moviedb.room.model.MovieTable
import com.test.moviedb.viewmodel.MovieApiViewModel
import com.test.moviedb.viewmodel.MovieApiViewModelFactory
import com.test.moviedb.viewmodel.MovieDbViewModel
import com.test.moviedb.viewmodel.MovieDbViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MoviePagingListFragment : BaseFragment(), ItemClickListener {

    private lateinit var dao: DbDao

    private val movieApiViewModel: MovieApiViewModel by viewModels { MovieApiViewModelFactory(dao) }
    private val movieDbViewModel: MovieDbViewModel by viewModels { MovieDbViewModelFactory(dao) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao = RoomDataBase.getDatabase(requireContext()).movieDao()

        val moviePagingAdapter = MoviePagingAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = moviePagingAdapter.withLoadStateHeaderAndFooter(
            header = MoviesLoadStateAdapter { moviePagingAdapter.retry() },
            footer = MoviesLoadStateAdapter { moviePagingAdapter.retry() }
        )


        if (isOnline(requireContext())) {
            lifecycleScope.launch {
                movieApiViewModel.passengers.collectLatest {
                    moviePagingAdapter.submitData(it)
                }
            }
        } else {
            lifecycleScope.launch {
                movieDbViewModel.data.collectLatest {
                    moviePagingAdapter.submitData(it)
                }
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

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }
}