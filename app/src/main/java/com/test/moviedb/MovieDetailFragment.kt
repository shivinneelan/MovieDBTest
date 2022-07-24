package com.test.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.test.moviedb.room.model.MovieTable
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.movie_list_item.view.*

private const val MOVIE_DATA = "movieData"

class MovieDetailFragment : Fragment() {
    private var data: MovieTable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            data = it.getParcelable(MOVIE_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data?.let {
            tvName.text = it.first_name
            tvDescription.text = it.email
            if (it.avatar.isNotEmpty()) {
                Glide.with(this)
                    .load(it.avatar)
                    .into(ivImageView)
            } else {
                ivImageView.setImageResource(R.drawable.ic_launcher_background)
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(data: MovieTable) =
            MovieDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(MOVIE_DATA, data)
                }
            }
    }
}