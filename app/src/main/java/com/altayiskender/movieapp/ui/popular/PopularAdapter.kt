package com.altayiskender.movieapp.ui.popular

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage
import kotlinx.android.synthetic.main.list_item_home.view.*
import timber.log.Timber

class PopularAdapter(
    private val inflater: LayoutInflater,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.Adapter<PopularAdapter.HomeViewHolder>() {
    private var movies = listOf<Movie>()

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(p0: HomeViewHolder, p1: Int) {
        p0.bind(movies[p1])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            inflater.inflate(R.layout.list_item_home, parent, false),
            onInteractionListener

        )
    }


    fun setMovies(newMovies: List<Movie>) {
        val oldList = movies
        val diffResult = DiffUtil.calculateDiff(MoviesDiffCallback(oldList, newMovies))
        this.movies = newMovies
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnInteractionListener {

        fun onItemClicked(movieId: Long, movieTitle: String)

    }

    class HomeViewHolder(
        private val view: View,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) {
            view.homePosterImageView.loadImage(getPosterUrl(movie.posterPath))
            view.homeNameTextView.text = movie.title
            view.setOnClickListener {
                onInteractionListener.onItemClicked(movie.id!!, movie.title!!)
            }
        }
    }

    class MoviesDiffCallback(private val oldList: List<Movie>, private val newList: List<Movie>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val titleOld = oldList[oldPosition].title
            val titleNew = newList[newPosition].title

            return titleOld == titleNew
        }
    }
}