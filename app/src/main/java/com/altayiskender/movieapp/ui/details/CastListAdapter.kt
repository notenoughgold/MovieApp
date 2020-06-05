package com.altayiskender.movieapp.ui.details


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altayiskender.movieapp.databinding.CardCastBinding
import com.altayiskender.movieapp.models.CastOfShow
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage


class CastListAdapter(
    private val onInteractionListener: OnInteractionListener
) :

    ListAdapter<CastOfShow, CastListAdapter.CastViewHolder>(CastItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            CardCastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onInteractionListener
        )
    }
//            RecyclerView.ViewHolder {
//        return when (viewType) {
//            VIEW_TYPE_MOVIE_DETAIL -> {
//                DetailsViewHolder(
//                    CardDetailsBinding.inflate(
//                        LayoutInflater.from(parent.context), parent, false
//                    )
//                )
//            }
//            VIEW_TYPE_MOVIE_CAST -> {
//                CastViewHolder(
//                    CardCastBinding.inflate(
//                        LayoutInflater.from(parent.context), parent, false
//                    ),
//                    onInteractionListener
//                )
//            }
//            VIEW_TYPE_MOVIE_DIRECTOR -> {
//                DirectorViewHolder(
//                    CardDirectorBinding.inflate(
//                        LayoutInflater.from(parent.context), parent, false
//                    ),
//                    onInteractionListener
//                )
//            }
//            VIEW_TYPE_MOVIE_HEADER -> {
//                HeaderViewHolder(
//                    HeaderRecyclerViewBinding.inflate(
//                        LayoutInflater.from(parent.context), parent, false
//                    )
//
//                )
//            }
//            else -> {
//                throw IllegalArgumentException("Invalid view type, value of $viewType")
//            }
//        }
//    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CastItemDiffCallback : DiffUtil.ItemCallback<CastOfShow>() {
        override fun areItemsTheSame(oldItem: CastOfShow, newItem: CastOfShow): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CastOfShow, newItem: CastOfShow): Boolean {
            return oldItem.id == newItem.id && oldItem.character == newItem.character && oldItem.name == newItem.name && oldItem.profilePath == newItem.profilePath
        }

    }

    class CastViewHolder(
        private var binding: CardCastBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: CastOfShow?) {
            if (cast == null) {
                return
            }

            binding.castPosterIv.loadImage(getPosterUrl(cast.profilePath))
            binding.castNameTv.text = cast.name
            binding.castCharacterTv.text = cast.character

            binding.root.apply {
                setOnClickListener {
                    onInteractionListener.onItemClicked(
                        cast.id,
                        cast.name
                    )
                }
            }
        }

    }

    interface OnInteractionListener {

        fun onItemClicked(castId: Long, castName: String)

    }
}