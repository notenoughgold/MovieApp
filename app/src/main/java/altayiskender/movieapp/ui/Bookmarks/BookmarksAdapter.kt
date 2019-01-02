package altayiskender.movieapp.ui.Bookmarks

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.R
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BookmarksAdapter (private val inflater: LayoutInflater,private val onInteractionListener: OnInteractionListener)
    :RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>(){

    private var bookmarks = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksAdapter.BookmarksViewHolder {
        return BookmarksViewHolder(inflater.inflate(R.layout.card_bookmarks,parent,false),onInteractionListener)
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    override fun onBindViewHolder(holder: BookmarksAdapter.BookmarksViewHolder, position: Int) {
        holder.bind(bookmarks[position])
    }
    class BookmarksViewHolder(itemView:View,private val onInteractionListener: OnInteractionListener):RecyclerView.ViewHolder(itemView){
        private val posterIv: ImageView = itemView.findViewById(R.id.bookmarkPosterIv)
        private val titleTv: TextView = itemView.findViewById(R.id.bookmarkTitleTv)
        private val yearTv: TextView = itemView.findViewById(R.id.bookmarkReleaseYearTv)

        fun bind(bookmark: Movie){
            posterIv.loadImage(getPosterUrl(bookmark.posterPath),posterIv)
            titleTv.text = bookmark.title
            yearTv.text = bookmark.releaseDate

            itemView.setOnClickListener {
                onInteractionListener.onItemClicked(bookmark.id!!,bookmark.title!!)
            }
        }
    }

    fun setBookmarks(bookmarks: List<Movie>) {
        this.bookmarks = bookmarks
        notifyDataSetChanged()
    }

    interface OnInteractionListener {

        fun onItemClicked(movieId: Long,movieTitle:String)

    }
}