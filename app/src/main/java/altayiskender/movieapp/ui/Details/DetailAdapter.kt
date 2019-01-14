package altayiskender.movieapp.ui.Details

import altayiskender.movieapp.Models.CastOfShow
import altayiskender.movieapp.Pojos.CrewOfShow
import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Utils.getBackdropUrl
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage
import altayiskender.movieapp.databinding.CardCastBinding
import altayiskender.movieapp.databinding.CardDetailsBinding
import altayiskender.movieapp.databinding.CardDirectorBinding
import altayiskender.movieapp.databinding.HeaderRecyclerViewBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip

private const val VIEW_TYPE_MOVIE_DETAIL = 0
private const val VIEW_TYPE_MOVIE_CAST = 2
private const val VIEW_TYPE_MOVIE_DIRECTOR = 1
private const val VIEW_TYPE_MOVIE_HEADER = 3


class DetailAdapter(private val onInteractionListener: OnInteractionListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var movie: Movie? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MOVIE_DETAIL -> {
                DetailsViewHolder(
                        CardDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            VIEW_TYPE_MOVIE_CAST -> {
                CastViewHolder(
                        CardCastBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                        onInteractionListener
                )
            }
            VIEW_TYPE_MOVIE_DIRECTOR -> {
                DirectorViewHolder(
                        CardDirectorBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                        onInteractionListener
                )
            }
            VIEW_TYPE_MOVIE_HEADER -> {
                HeaderViewHolder(
                        HeaderRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

    private fun getDirector(): CrewOfShow? {
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

    class DetailsViewHolder(private var binding: CardDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            if (movie == null) {
                return
            }
            if (movie.backdropPath?.isNotEmpty() == true) {
                binding.detailsImageView.loadImage(getBackdropUrl(movie.backdropPath))
            }

            if (movie.genres?.isNotEmpty() == true) {
                binding.detailsGenresCg.visibility = View.VISIBLE
                var genresList = movie.genres!!.map { it.name }
                for (str in genresList) {
                    var chip = Chip(binding.detailsGenresCg.context)
                    chip.text = str
                    chip.isCheckable = false
                    chip.isClickable = false

                    binding.detailsGenresCg.addView(chip)
                }
            } else {
                binding.detailsGenresCg.visibility = View.GONE
            }

            if (movie.overview?.isEmpty() == true) {
                binding.detailsDescriptionContainer.visibility = View.GONE
            } else {
                binding.detailsDescriptionContainer.visibility = View.VISIBLE
                binding.detailsDescriptionTv.text = movie.overview
                binding.detailsDescriptionContainer.setOnClickListener {
                    if (binding.detailsDescriptionMoreTv.visibility == View.INVISIBLE) {
                        binding.detailsDescriptionMoreTv.visibility = View.VISIBLE
                        binding.detailsDescriptionLessTv.visibility = View.INVISIBLE
                        binding.detailsDescriptionTv.maxLines = 5
                    } else {
                        binding.detailsDescriptionTv.maxLines = 30
                        binding.detailsDescriptionMoreTv.visibility = View.INVISIBLE
                        binding.detailsDescriptionLessTv.visibility = View.VISIBLE
                    }
                }
            }

            binding.detailsReleaseTv.text = movie.releaseDate

            binding.detailsRatingTv.text = "${movie.voteAverage} / 10"

            binding.detailsRuntimeTv.text = "${movie.runtime} m"

        }
    }

    class DirectorViewHolder(private var binding: CardDirectorBinding, private val onInteractionListener: OnInteractionListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(director: CrewOfShow?) {
            if (director == null) {
                return
            }
            binding.directorProfileIv.loadImage(getPosterUrl(director.profilePath))
            binding.directorNameTv.text = director.name
            binding.root.apply { setOnClickListener { onInteractionListener.onItemClicked(director.id, director.name) } }
        }
    }

    class HeaderViewHolder(private var binding: HeaderRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            if (position == 1) {
                binding.headerTv.text = "Director"
            } else if (position == 3) {
                binding.headerTv.text = "Cast"
            }
        }
    }

    class CastViewHolder(private var binding: CardCastBinding, private val onInteractionListener: OnInteractionListener)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: CastOfShow?) {
            if (cast == null) {
                return
            }

            binding.castPosterIv.loadImage(getPosterUrl(cast.profilePath))
            binding.castNameTv.text = cast.name
            binding.castCharacterTv.text = cast.character

            binding.root.apply { setOnClickListener { onInteractionListener.onItemClicked(cast.id, cast.name) } }
        }

    }

    interface OnInteractionListener {

        fun onItemClicked(castId: Long, castName: String)

    }
}