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

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class CastItemDiffCallback : DiffUtil.ItemCallback<CastOfShow>() {
        override fun areItemsTheSame(oldItem: CastOfShow, newItem: CastOfShow): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CastOfShow, newItem: CastOfShow): Boolean {
            return oldItem == newItem
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