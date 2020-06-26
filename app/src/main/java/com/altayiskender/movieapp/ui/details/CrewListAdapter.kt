package com.altayiskender.movieapp.ui.details


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.altayiskender.movieapp.databinding.CardCastBinding
import com.altayiskender.movieapp.models.CastOfShow
import com.altayiskender.movieapp.models.CrewOfShow
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.utils.getBackdropUrl
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage


class CrewListAdapter(
    private val onInteractionListener: OnInteractionListener
) :

    ListAdapter<CrewOfShow, CrewListAdapter.CrewViewHolder>(CrewItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewViewHolder {
        return CrewViewHolder(
            CardCastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onInteractionListener
        )
    }

    override fun onBindViewHolder(holder: CrewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CrewItemDiffCallback : DiffUtil.ItemCallback<CrewOfShow>() {
        override fun areItemsTheSame(oldItem: CrewOfShow, newItem: CrewOfShow): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CrewOfShow, newItem: CrewOfShow): Boolean {
            return oldItem == newItem
        }

    }

    class CrewViewHolder(
        private var binding: CardCastBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: CrewOfShow?) {
            if (cast == null) {
                return
            }

            binding.castPosterIv.loadImage(getPosterUrl(cast.profilePath))
            binding.castNameTv.text = cast.name
            binding.castCharacterTv.text = cast.job

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