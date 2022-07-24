package com.test.moviedb.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.moviedb.databinding.MovieListItemBinding
import com.test.moviedb.room.model.MovieTable


class MoviePagingAdapter :
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
            txtMovieName.text = item.first_name
            txtDescription.text = item.email

            imgProfile.loadImage(item.avatar)
        }
    }

    object PassengersComparator : DiffUtil.ItemCallback<MovieTable>() {
        override fun areItemsTheSame(oldItem: MovieTable, newItem: MovieTable): Boolean {
            return oldItem.id == newItem.id
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