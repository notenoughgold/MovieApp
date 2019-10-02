package com.altayiskender.movieapp.ui.people


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.models.CastAsPerson
import com.altayiskender.movieapp.models.PeopleResponse
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage
import kotlinx.android.synthetic.main.card_people_details.view.*
import kotlinx.android.synthetic.main.card_people_works.view.*

private const val VIEW_TYPE_PEOPLE_DETAIL = 0
private const val VIEW_TYPE_PEOPLE_CAST = 1

class PeopleAdapter(
    private val layoutInflater: LayoutInflater,
    private val onInteractionListener: OnInteractionListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var peopleResponse: PeopleResponse? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PEOPLE_DETAIL -> {
                DetailsViewHolder(
                    layoutInflater.inflate(
                        R.layout.card_people_details,
                        parent,
                        false
                    )

                )
            }
            VIEW_TYPE_PEOPLE_CAST -> {
                CastViewHolder(
                    layoutInflater.inflate(
                        R.layout.card_people_works,
                        parent,
                        false
                    ),
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
                val cast = peopleResponse?.movieCredits?.cast
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
            peopleResponse?.movieCredits?.cast?.size?.plus(1) ?: 1
        }
    }

    fun setPeople(peopleResponse: PeopleResponse) {
        this.peopleResponse = peopleResponse
        notifyDataSetChanged()
    }

    class DetailsViewHolder(private var view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(peopleResponse: PeopleResponse?) {
            if (peopleResponse == null) {
                return
            }
            if (peopleResponse.profilePath?.isNotEmpty() == true) {
                view.people_photo_iv.loadImage(getPosterUrl(peopleResponse.profilePath))
            }

            if (peopleResponse.birthday?.isNotEmpty() == true) {
                view.people_birthday_tv.visibility = View.VISIBLE
                view.people_birthday_tv.text = peopleResponse.birthday

            } else {
                view.people_birthday_tv.visibility = View.GONE
                view.people_birthday_tv_title.visibility = View.GONE
            }

            if (peopleResponse.placeOfBirth?.isNotEmpty() == true) {
                view.people_birthplace_tv.visibility = View.VISIBLE
                view.people_birthplace_tv.text = peopleResponse.placeOfBirth

            } else {
                view.people_birthplace_tv.visibility = View.GONE
                view.people_birthplace_tv_title.visibility = View.GONE
            }


            if (peopleResponse.homepage?.isNotEmpty() == true) {
                view.people_homepageurl_tv.visibility = View.VISIBLE
                view.people_homepageurl_tv.text = peopleResponse.homepage


            } else {
                view.people_homepageurl_tv.visibility = View.GONE
                view.people_homepageurl_tv_title.visibility = View.GONE
            }

            if (peopleResponse.biography?.isEmpty() == true) {
                view.peopleBiographyContainer.visibility = View.GONE
            } else {
                view.peopleBiographyContainer.visibility = View.VISIBLE
                view.people_bio_tv.text = peopleResponse.biography
                view.peopleBiographyContainer.setOnClickListener {
                    if (view.peopleDescriptionMoreTv.visibility == View.INVISIBLE) {
                        view.peopleDescriptionMoreTv.visibility = View.VISIBLE
                        view.peopleDescriptionLessTv.visibility = View.INVISIBLE
                        view.people_bio_tv.maxLines = 5
                    } else {
                        view.people_bio_tv.maxLines = 30
                        view.peopleDescriptionMoreTv.visibility = View.INVISIBLE
                        view.peopleDescriptionLessTv.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    class CastViewHolder(
        private val view: View,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(cast: CastAsPerson?) {
            if (cast == null) {
                return
            }

            view.workPosterIv.loadImage(getPosterUrl(cast.posterPath))
            view.workNameTv.text = cast.title
            view.castCharacterTv.text = cast.character
            var airDate: String? = null
            if (!cast.releaseDate.isNullOrEmpty()) {
                airDate = cast.releaseDate
            } else if (!cast.firstAirDate.isNullOrEmpty()) {
                airDate = cast.firstAirDate
            }
            view.workYearTv.text = airDate

            view.setOnClickListener {
                onInteractionListener.onItemClicked(
                    cast.id!!,
                    cast.title!!
                )
            }
        }

    }

    interface OnInteractionListener {

        fun onItemClicked(castId: Long, castTitle: String)

    }
}