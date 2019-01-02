package altayiskender.movieapp.ui.Popular

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.R
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class PopularAdapter(private val inflater: LayoutInflater, private val onInteractionListener: OnInteractionListener)
    : androidx.recyclerview.widget.RecyclerView.Adapter<PopularAdapter.HomeViewHolder>() {
    private var movies = listOf<Movie>()

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(p0: HomeViewHolder, p1: Int) {
        p0.bind(movies[p1])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HomeViewHolder(
                    inflater.inflate(R.layout.list_item_home, parent, false),
                    onInteractionListener
            )

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    interface OnInteractionListener {

        fun onItemClicked(movieId: Long,movieTitle:String)

    }

    class HomeViewHolder(itemView: View, private val onInteractionListener: OnInteractionListener) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        private val posterImageView: ImageView = itemView.findViewById(R.id.homePosterImageView)
        private val homeNameTextView: TextView = itemView.findViewById(R.id.homeNameTextView)
        fun bind(movie: Movie) {
            posterImageView.loadImage(getPosterUrl(movie.posterPath), posterImageView)

            homeNameTextView.text = movie.title
            itemView.setOnClickListener {
                onInteractionListener.onItemClicked(movie.id!!,movie.title!!)
            }
        }
    }

}