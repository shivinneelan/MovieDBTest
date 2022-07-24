package com.test.moviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.moviedb.R
import com.test.moviedb.listeners.ItemClickListener
import com.test.moviedb.room.model.MovieTable
import kotlinx.android.synthetic.main.movie_list_item.view.*


class MovieListAdapter(
    private val context: Context,
    private val movieList: List<MovieTable>,
    private val listener: ItemClickListener

) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieData: MovieTable = movieList[holder.adapterPosition]

        holder.itemView.txtMovieName.text = movieData.first_name
        holder.itemView.txtDescription.text = movieData.email

        if (movieData.avatar.isNotEmpty()) {
            Glide.with(context)
                .load(movieData.avatar)
                .into(holder.itemView.imgProfile)
        } else {
            holder.itemView.imgProfile.setImageResource(R.drawable.ic_launcher_background)
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(movieData)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}