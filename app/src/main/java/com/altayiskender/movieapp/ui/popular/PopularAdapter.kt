package com.altayiskender.movieapp.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import com.altayiskender.movieapp.databinding.ListItemHomeBinding
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage

class PopularAdapter(
    private val inflater: LayoutInflater,
    private val onInteractionListener: OnInteractionListener
) : androidx.recyclerview.widget.RecyclerView.Adapter<PopularAdapter.HomeViewHolder>() {
    private var movies = listOf<Movie>()

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(p0: HomeViewHolder, p1: Int) {
        p0.bind(movies[p1])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ListItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onInteractionListener

        )
    }


    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    interface OnInteractionListener {

        fun onItemClicked(movieId: Long, movieTitle: String)

    }

    class HomeViewHolder(
        private val binding: ListItemHomeBinding,
        private val onInteractionListener: OnInteractionListener
    ) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.homePosterImageView.loadImage(getPosterUrl(movie.posterPath))
            binding.homeNameTextView.text = movie.title
            binding.root.setOnClickListener {
                onInteractionListener.onItemClicked(movie.id!!, movie.title!!)
            }
        }
    }

}