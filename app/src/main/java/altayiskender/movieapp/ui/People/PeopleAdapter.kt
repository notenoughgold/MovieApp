package altayiskender.movieapp.ui.People

import altayiskender.movieapp.Pojos.CastAsPerson
import altayiskender.movieapp.Pojos.PeopleResponse
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage
import altayiskender.movieapp.databinding.CardPeopleDetailsBinding
import altayiskender.movieapp.databinding.CardPeopleWorksBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val VIEW_TYPE_PEOPLE_DETAIL = 0
private const val VIEW_TYPE_PEOPLE_CAST = 1

class PeopleAdapter(private val onInteractionListener: OnInteractionListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var peopleResponse: PeopleResponse? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PEOPLE_DETAIL -> {
                DetailsViewHolder(
                        CardPeopleDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                )
            }
            VIEW_TYPE_PEOPLE_CAST -> {
                CastViewHolder(
                        CardPeopleWorksBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                        onInteractionListener
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid view type, value of $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_PEOPLE_DETAIL -> {
                (holder as? DetailsViewHolder)?.bind(peopleResponse)
            }
            VIEW_TYPE_PEOPLE_CAST -> {
                val cast = peopleResponse?.combinedCredits?.cast
                val size = cast?.size
                if (size != null && position <= size && holder is CastViewHolder) {
                    holder.bind(cast[position - 1])
                }
            }
        }
    }

    override fun getItemViewType(position: Int) =
            when (position) {
                0 -> VIEW_TYPE_PEOPLE_DETAIL
                else -> VIEW_TYPE_PEOPLE_CAST
            }

    override fun getItemCount(): Int {
        return if (peopleResponse == null) {
            0
        } else {
            peopleResponse?.combinedCredits?.cast?.size?.plus(1) ?: 1
        }
    }

    fun setPeople(peopleResponse: PeopleResponse) {
        this.peopleResponse = peopleResponse
        notifyDataSetChanged()
    }

    class DetailsViewHolder(private var binding: CardPeopleDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(peopleResponse: PeopleResponse?) {
            if (peopleResponse == null) {
                return
            }
            if (peopleResponse.profilePath?.isNotEmpty() == true) {
                binding.peoplePhotoIv.loadImage(getPosterUrl(peopleResponse.profilePath))
            }

            if (peopleResponse.birthday?.isNotEmpty() == true) {
                binding.peopleBirthdayTv.visibility = View.VISIBLE
                binding.peopleBirthdayTv.text = peopleResponse.birthday

            } else {
                binding.peopleBirthdayTv.visibility = View.GONE
                binding.peopleBirthdayTvTitle.visibility = View.GONE
            }

            if (peopleResponse.placeOfBirth?.isNotEmpty() == true) {
                binding.peopleBirthplaceTv.visibility = View.VISIBLE
                binding.peopleBirthplaceTv.text = peopleResponse.placeOfBirth

            } else {
                binding.peopleBirthplaceTv.visibility = View.GONE
                binding.peopleBirthdayTvTitle.visibility = View.GONE
            }


            if (peopleResponse.homepage?.isNotEmpty() == true) {
                binding.peopleHomepageurlTv.visibility = View.VISIBLE
                binding.peopleHomepageurlTv.text = peopleResponse.homepage


            } else {
                binding.peopleHomepageurlTv.visibility = View.GONE
                binding.peopleHomepageurlTvTitle.visibility = View.GONE
            }

            if (peopleResponse.biography?.isEmpty() == true) {
                binding.peopleBiographyContainer.visibility = View.GONE
            } else {
                binding.peopleBiographyContainer.visibility = View.VISIBLE
                binding.peopleBioTv.text = peopleResponse.biography
                binding.peopleBiographyContainer.setOnClickListener {
                    if (binding.peopleDescriptionMoreTv.visibility == View.INVISIBLE) {
                        binding.peopleDescriptionMoreTv.visibility = View.VISIBLE
                        binding.peopleDescriptionLessTv.visibility = View.INVISIBLE
                        binding.peopleBioTv.maxLines = 5
                    } else {
                        binding.peopleBioTv.maxLines = 30
                        binding.peopleDescriptionMoreTv.visibility = View.INVISIBLE
                        binding.peopleDescriptionLessTv.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    class CastViewHolder(private val binding: CardPeopleWorksBinding, private val onInteractionListener: OnInteractionListener)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: CastAsPerson?) {
            if (cast == null) {
                return
            }

            binding.workPosterIv.loadImage(getPosterUrl(cast.posterPath))
            binding.workNameTv.text = cast.title
            binding.castCharacterTv.text = cast.character
            var airDate: String? = null
            if (!cast.releaseDate.isNullOrEmpty()) {
                airDate = cast.releaseDate
            } else if (!cast.firstAirDate.isNullOrEmpty()) {
                airDate = cast.firstAirDate
            }
            binding.workYearTv.text = airDate

            binding.root.apply { setOnClickListener { onInteractionListener.onItemClicked(cast.id!!, cast.title!!) } }
        }

    }

    interface OnInteractionListener {

        fun onItemClicked(castId: Long, castTitle: String)

    }
}