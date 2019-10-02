package com.altayiskender.movieapp.ui.bookmarks


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.utils.getPosterUrl
import com.altayiskender.movieapp.utils.loadImage
import kotlinx.android.synthetic.main.card_bookmarks.view.*


class BookmarksAdapter(
    private val layoutInflater: LayoutInflater,
    private val onInteractionListener: OnInteractionListener
) :
    RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {

    private var bookmarks = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(
            layoutInflater.inflate(
                R.layout.card_bookmarks, parent,
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
        private val view: View,
        private val onInteractionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(bookmark: Movie) {
            view.bookmarkPosterIv.loadImage(getPosterUrl(bookmark.posterPath))
            view.bookmarkTitleTv.text = bookmark.title
            view.bookmarkReleaseYearTv.text = bookmark.releaseDate

            view.setOnClickListener {
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