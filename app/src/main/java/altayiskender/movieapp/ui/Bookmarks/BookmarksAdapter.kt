package altayiskender.movieapp.ui.Bookmarks

import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.Utils.getPosterUrl
import altayiskender.movieapp.Utils.loadImage
import altayiskender.movieapp.databinding.CardBookmarksBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class BookmarksAdapter(private val onInteractionListener: OnInteractionListener)
    :RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>(){

    private var bookmarks = listOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksAdapter.BookmarksViewHolder {
        return BookmarksViewHolder(CardBookmarksBinding.inflate(LayoutInflater.from(parent.context), parent, false), onInteractionListener)
    }

    override fun getItemCount(): Int {
        return bookmarks.size
    }

    override fun onBindViewHolder(holder: BookmarksAdapter.BookmarksViewHolder, position: Int) {
        holder.bind(bookmarks[position])
    }

    class BookmarksViewHolder(private val binding: CardBookmarksBinding, private val onInteractionListener: OnInteractionListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Movie){
            binding.bookmarkPosterIv.loadImage(getPosterUrl(bookmark.posterPath))
            binding.bookmarkTitleTv.text = bookmark.title
            binding.bookmarkReleaseYearTv.text = bookmark.releaseDate

            binding.root.setOnClickListener {
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