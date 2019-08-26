package com.altayiskender.movieapp.ui.bookmarks


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altayiskender.movieapp.databinding.CardBookmarksBinding
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage


class BookmarksAdapter(private val onInteractionListener: OnInteractionListener) :
    RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    private var bookmarks = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(
            CardBookmarksBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onInteractionListener
        )
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        holder.bind(bookmarks[position])
    }

    class BookmarksViewHolder(
        private val binding: CardBookmarksBinding,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Movie) {
            binding.bookmarkPosterIv.loadImage(getPosterUrl(bookmark.posterPath))
            binding.bookmarkTitleTv.text = bookmark.title
            binding.bookmarkReleaseYearTv.text = bookmark.releaseDate

            binding.root.setOnClickListener {
                onInteractionListener.onItemClicked(bookmark.id!!, bookmark.title!!)
            }
        }
    }

    fun setBookmarks(bookmarks: List<Movie>) {
        this.bookmarks = bookmarks
        notifyDataSetChanged()
    }

    interface OnInteractionListener {

        fun onItemClicked(movieId: Long, movieTitle: String)

    }
}