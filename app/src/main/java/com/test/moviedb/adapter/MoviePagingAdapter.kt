package com.test.moviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.moviedb.databinding.MovieListItemBinding
import com.test.moviedb.listeners.ItemClickListener
import com.test.moviedb.room.model.MovieTable


class MoviePagingAdapter(
    private val listener: ItemClickListener
) :
    PagingDataAdapter<MovieTable, MoviePagingAdapter.PassengersViewHolder>(PassengersComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PassengersViewHolder {
        return PassengersViewHolder(
            MovieListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: PassengersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindPassenger(it) }
    }

    inner class PassengersViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindPassenger(item: MovieTable) = with(binding) {
            txtMovieName.text = item.name
            txtDescription.text = item.trips
            txtId.text = item._id

//            if(!item.avatar.isNullOrEmpty())
//                imgProfile.loadImage(item.avatar)
//            else
                imgProfile.loadImage("https://upload.wikimedia.org/wikipedia/en/thumb/6/6b/Singapore_Airlines_Logo_2.svg/250px-Singapore_Airlines_Logo_2.svg.png")

            cardView.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    object PassengersComparator : DiffUtil.ItemCallback<MovieTable>() {
        override fun areItemsTheSame(oldItem: MovieTable, newItem: MovieTable): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: MovieTable, newItem: MovieTable): Boolean {
            return oldItem == newItem
        }
    }

    fun ImageView.loadImage(url: String) {
        Glide.with(this)
            .load(url)
            .into(this)
    }
}