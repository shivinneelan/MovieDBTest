package com.test.moviedb

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.test.moviedb.viewmodel.CommonViewModel
import androidx.fragment.app.viewModels
import com.test.moviedb.Constants.FRAGMENT_MOVIE_DETAIL
import com.test.moviedb.adapter.MovieListAdapter
import com.test.moviedb.api.Api
import com.test.moviedb.api.RetrofitHelper
import com.test.moviedb.listeners.ItemClickListener
import com.test.moviedb.listeners.OnFragmentInteractionListener
import com.test.moviedb.room.model.MovieTable
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class MovieListFragment : BaseFragment(), ItemClickListener {

    private val viewModel: CommonViewModel by viewModels()

    private fun setObservers() {

        viewModel.movieListLiveData.observe(this, { result ->
            if (result.isNotEmpty()) {
                MovieListAdapter(context = requireContext(), result, this).also {
                    recyclerView.adapter = it
                }
            } else {
                loadMovieList()
            }
        })

        viewModel.movieInsertLiveData.observe(this,{
            if(it.status == viewModel.STATUS_SUCCESS){
                Log.d("TEST_S","Data Insertion success")
                viewModel.getMovieLists()

            }else{
                Log.d("TEST_S","Data Insertion failed")
            }
        })

    }

    private fun cancelObservers() {
        viewModel.movieListLiveData.removeObservers(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMovieLists()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MovieListFragment().apply {
            }
    }

    override fun onDestroy() {
        cancelObservers()
        super.onDestroy()

    }


    @DelicateCoroutinesApi
    private fun loadMovieList() {
        Log.d("TEST_S", "loadMovieList")
        val quotesApi = RetrofitHelper.getInstance().create(Api::class.java)
        // launching a new coroutine
        GlobalScope.launch {
            try {
                val result = quotesApi.getMovieList("1")
                Log.d("TEST_S", "Result : ")
                if(result.data.isNotEmpty()){
                    viewModel.insertMovieList(result.data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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