package altayiskender.movieapp.ui.People

import altayiskender.movieapp.Pojos.CastAsPerson
import altayiskender.movieapp.Pojos.PeopleResponse
import altayiskender.movieapp.R
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

private const val VIEW_TYPE_PEOPLE_DETAIL = 0
private const val VIEW_TYPE_PEOPLE_CAST = 1

class PeopleAdapter(private val inflater: LayoutInflater, private val onInteractionListener: OnInteractionListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var peopleResponse: PeopleResponse? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PEOPLE_DETAIL -> {
                DetailsViewHolder(
                        inflater.inflate(R.layout.card_people_details, parent, false)
                )
            }
            VIEW_TYPE_PEOPLE_CAST -> {
                CastViewHolder(
                        inflater.inflate(R.layout.card_people_works, parent, false)
                        , onInteractionListener
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

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val peopleBirthdayTitleTv: TextView = itemView.findViewById(R.id.people_birthday_tv_title)
        private val peopleBirthdayTv: TextView = itemView.findViewById(R.id.people_birthday_tv)
        private val peopleBirthplaceTitleTv: TextView = itemView.findViewById(R.id.people_birthplace_tv_title)
        private val peopleBirthplaceTv: TextView = itemView.findViewById(R.id.people_birthplace_tv)
        private val peopleBioContainer: View = itemView.findViewById(R.id.peopleBiographyContainer)
        private val peopleDescriptionMoreTv: TextView = itemView.findViewById(R.id.peopleDescriptionMoreTv)
        private val peopleDescriptionLessTv: TextView = itemView.findViewById(R.id.peopleDescriptionLessTv)
        private val peopleHomepageUrlTitleTv: TextView = itemView.findViewById(R.id.people_homepageurl_tv_title)
        private val peopleHomepageUrlTv: TextView = itemView.findViewById(R.id.people_homepageurl_tv)
        private val peopleBioTv: TextView = itemView.findViewById(R.id.people_bio_tv)
        private val peoplePhoto: ImageView = itemView.findViewById(R.id.people_photo_iv)


        fun bind(peopleResponse: PeopleResponse?) {
            if (peopleResponse == null) {
                return
            }
            if (peopleResponse.profilePath?.isNotEmpty() == true) {
                peoplePhoto.loadImage(getPosterUrl(peopleResponse.profilePath), peoplePhoto)
            }

            if (peopleResponse.birthday?.isNotEmpty() == true) {
                peopleBirthdayTv.visibility = View.VISIBLE
                peopleBirthdayTv.text = peopleResponse.birthday

            } else {
                peopleBirthdayTv.visibility = View.GONE
                peopleBirthdayTitleTv.visibility = View.GONE
            }

            if (peopleResponse.placeOfBirth?.isNotEmpty() == true) {
                peopleBirthplaceTv.visibility = View.VISIBLE
                peopleBirthplaceTv.text = peopleResponse.placeOfBirth

            } else {
                peopleBirthplaceTv.visibility = View.GONE
                peopleBirthplaceTitleTv.visibility = View.GONE
            }


            if (peopleResponse.homepage?.isNotEmpty() == true) {
                peopleHomepageUrlTv.visibility = View.VISIBLE
                peopleHomepageUrlTv.text = peopleResponse.homepage


            } else {
                peopleHomepageUrlTv.visibility = View.GONE
                peopleHomepageUrlTitleTv.visibility = View.GONE
            }

            if (peopleResponse.biography?.isEmpty() == true) {
                peopleBioContainer.visibility = View.GONE
            } else {
                peopleBioContainer.visibility = View.VISIBLE
                peopleBioTv.text = peopleResponse.biography
                peopleBioContainer.setOnClickListener {
                    if (peopleDescriptionMoreTv.visibility == View.INVISIBLE) {
                        peopleDescriptionMoreTv.visibility = View.VISIBLE
                        peopleDescriptionLessTv.visibility = View.INVISIBLE
                        peopleBioTv.maxLines = 5
                    } else {
                        peopleBioTv.maxLines = 1000
                        peopleDescriptionMoreTv.visibility = View.INVISIBLE
                        peopleDescriptionLessTv.visibility = View.VISIBLE
                    }
                }
            }


        }
    }

    class CastViewHolder(itemView: View, private val onInteractionListener: OnInteractionListener)
        : RecyclerView.ViewHolder(itemView) {

        private val workPosterIv: ImageView = itemView.findViewById(R.id.workPosterIv)
        private val workNameTv: TextView = itemView.findViewById(R.id.workNameTv)
        private val castCharacterTv: TextView = itemView.findViewById(R.id.castCharacterTv)
        private val workYearTv: TextView = itemView.findViewById(R.id.workYearTv)

        fun bind(cast: CastAsPerson?) {
            if (cast == null) {
                return
            }

            workPosterIv.loadImage(getPosterUrl(cast.posterPath), workPosterIv)
            workNameTv.text = cast.title
            castCharacterTv.text = cast.character
            var airDate: String? = null
            if (!cast.releaseDate.isNullOrEmpty()) {
                airDate = cast.releaseDate
            } else if (!cast.firstAirDate.isNullOrEmpty()) {
                airDate = cast.firstAirDate
            }
            workYearTv.text = airDate

            itemView.apply { setOnClickListener { onInteractionListener.onItemClicked(cast.id!!, cast.title!!) } }
        }

    }

    interface OnInteractionListener {

        fun onItemClicked(castId: Long, castTitle: String)

    }
}