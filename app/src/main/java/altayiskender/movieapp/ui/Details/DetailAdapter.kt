package altayiskender.movieapp.ui.Details

import altayiskender.movieapp.Models.CastOfShow
import altayiskender.movieapp.Pojos.CrewOfShow
import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.R
import altayiskender.movieapp.Utils.getBackdropUrl
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

private const val VIEW_TYPE_MOVIE_DETAIL = 0
private const val VIEW_TYPE_MOVIE_CAST = 2
private const val VIEW_TYPE_MOVIE_DIRECTOR = 1
private const val VIEW_TYPE_MOVIE_HEADER = 3


class DetailAdapter(private val inflater: LayoutInflater, private val onInteractionListener: OnInteractionListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movie: Movie? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MOVIE_DETAIL -> {
                DetailsViewHolder(
                        inflater.inflate(R.layout.card_details, parent, false)
                )
            }
            VIEW_TYPE_MOVIE_CAST -> {
                CastViewHolder(
                        inflater.inflate(R.layout.card_cast, parent, false),
                        onInteractionListener
                )
            }
            VIEW_TYPE_MOVIE_DIRECTOR -> {
                DirectorViewHolder(
                        inflater.inflate(R.layout.card_director, parent, false),
                        onInteractionListener
                )
            }
            VIEW_TYPE_MOVIE_HEADER -> {
                HeaderViewHolder(
                        inflater.inflate(R.layout.header_recycler_view, parent, false)
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid view type, value of $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_MOVIE_DETAIL -> {
                (holder as? DetailsViewHolder)?.bind(movie)
            }
            VIEW_TYPE_MOVIE_CAST -> {
                val cast = movie?.credits?.cast
                val size = cast?.size
                if (size != null && position <= size && holder is CastViewHolder) {
                    holder.bind(cast[position - 4])
                }
            }
            VIEW_TYPE_MOVIE_DIRECTOR -> {
                (holder as? DirectorViewHolder)?.bind(getDirector())
            }
            VIEW_TYPE_MOVIE_HEADER -> {
                (holder as? HeaderViewHolder)?.bind(position)
            }
        }
    }

    override fun getItemViewType(position: Int) =
            when (position) {
                0 -> VIEW_TYPE_MOVIE_DETAIL
                1, 3 -> VIEW_TYPE_MOVIE_HEADER
                2 -> VIEW_TYPE_MOVIE_DIRECTOR
                else -> VIEW_TYPE_MOVIE_CAST
            }

    override fun getItemCount(): Int {
        return if (movie == null) {
            0
        } else {
            val castSize = movie?.credits?.cast?.size
            return castSize!!.plus(4)

        }

    }

    fun getDirector(): CrewOfShow? {
        var director: CrewOfShow? = null
        var crewSize = movie?.credits?.crew?.size
        var directorPosition = 0
        while (directorPosition < crewSize!!) {
            if (movie?.credits?.crew?.get(directorPosition)!!.job != "Director") {
                directorPosition++
            } else if (movie?.credits?.crew?.get(directorPosition)!!.job == "Director") {
                director = movie?.credits?.crew?.get(directorPosition)
                break
            }

        }
        return director
    }

    fun setMovie(movie: Movie?) {
        this.movie = movie
        notifyDataSetChanged()
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val detailsImageView: ImageView = itemView.findViewById(R.id.detailsImageView)
        private val detailsGenresCg: ChipGroup = itemView.findViewById(R.id.detailsGenresCg)
        private val detailsDescriptionTv: TextView = itemView.findViewById(R.id.detailsDescriptionTv)
        private val detailsDescriptionContainer: View = itemView.findViewById(R.id.detailsDescriptionContainer)
        private val detailsDescriptionMoreTv: TextView = itemView.findViewById(R.id.detailsDescriptionMoreTv)
        private val detailsDescriptionLessTv: TextView = itemView.findViewById(R.id.detailsDescriptionLessTv)
        private val detailsReleaseTv: TextView = itemView.findViewById(R.id.detailsReleaseTv)
        private val detailsRatingTv: TextView = itemView.findViewById(R.id.detailsRatingTv)
        private val detailsRuntimeTv: TextView = itemView.findViewById(R.id.detailsRuntimeTv)

        fun bind(movie: Movie?) {
            if (movie == null) {
                return
            }
            if (movie.backdropPath?.isNotEmpty() == true) {
                detailsImageView.loadImage(getBackdropUrl(movie.backdropPath), detailsImageView)
            }

            if (movie.genres?.isNotEmpty() == true) {
                detailsGenresCg.visibility = View.VISIBLE
                var genresList = movie.genres!!.map { it.name }
                for( str in genresList){
                    var chip = Chip(detailsGenresCg.context)
                    chip.text = str
                    chip.isCheckable = false
                    chip.isClickable = false

                    detailsGenresCg.addView(chip)
                }
            }else{
                detailsGenresCg.visibility = View.GONE
            }

            if (movie.overview?.isEmpty() == true) {
                detailsDescriptionContainer.visibility = View.GONE
            } else {
                detailsDescriptionContainer.visibility = View.VISIBLE
                detailsDescriptionTv.text = movie.overview
                detailsDescriptionContainer.setOnClickListener {
                    if (detailsDescriptionMoreTv.visibility == View.INVISIBLE) {
                        detailsDescriptionMoreTv.visibility = View.VISIBLE
                        detailsDescriptionLessTv.visibility = View.INVISIBLE
                        detailsDescriptionTv.maxLines = 5
                    } else {
                        detailsDescriptionTv.maxLines = 1000
                        detailsDescriptionMoreTv.visibility = View.INVISIBLE
                        detailsDescriptionLessTv.visibility = View.VISIBLE
                    }
                }
            }

            movie.releaseDate?.let {
                detailsReleaseTv.text = it
            }

            movie.voteAverage?.let {
                detailsRatingTv.text = "${it} / 10"
            }

            movie.runtime?.let {
                detailsRuntimeTv.text = "${it} m"
            }
        }
    }

    class DirectorViewHolder(itemView: View,private val onInteractionListener: OnInteractionListener) : RecyclerView.ViewHolder(itemView) {
        private val directorNameTv: TextView = itemView.findViewById(R.id.directorNameTv)
        private val directorProfileIv: ImageView = itemView.findViewById(R.id.directorProfileIv)

        fun bind(director: CrewOfShow?) {
            if (director == null) {
                return
            }
            directorProfileIv.loadImage(getPosterUrl(director.profilePath), directorProfileIv)
            directorNameTv.text = director.name
            itemView.apply { setOnClickListener { onInteractionListener.onItemClicked(director.id, director.name) } }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val headerTv: TextView = itemView.findViewById(R.id.headerTv)
        fun bind(position: Int) {
            if (position == 1) {
                headerTv.text = "Director"
            } else if (position == 3) {
                headerTv.text = "Cast"
            }
        }
    }

    class CastViewHolder(itemView: View, private val onInteractionListener: OnInteractionListener)
        : RecyclerView.ViewHolder(itemView) {

        private val castProfileIv: ImageView = itemView.findViewById(R.id.castPosterIv)
        private val castNameTv: TextView = itemView.findViewById(R.id.castNameTv)
        private val castCharacterTv: TextView = itemView.findViewById(R.id.castCharacterTv)

        fun bind(cast: CastOfShow?) {
            if (cast == null) {
                return
            }

            castProfileIv.loadImage(getPosterUrl(cast.profilePath), castProfileIv)

            castNameTv.text = cast.name
            castCharacterTv.text = cast.character

            itemView.apply { setOnClickListener { onInteractionListener.onItemClicked(cast.id, cast.name) } }
        }

    }

    interface OnInteractionListener {

        fun onItemClicked(castId: Long, castName: String)

    }
}