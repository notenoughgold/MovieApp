package com.altayiskender.movieapp.ui.bookmarks


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.databinding.FragmentBookmarksBinding
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.ui.popular.ARG_MOVIE
import com.altayiskender.movieapp.ui.popular.ARG_MOVIE_NAME
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber


class BookmarksFragment : Fragment(), KodeinAware, BookmarksAdapter.OnInteractionListener {
    override val kodein by kodein()

    private val viewModelFactory: BookmarksViewModelFactory by instance()
    lateinit var bookmarksViewModel: BookmarksViewModel
    private lateinit var bookmarksAdapter: BookmarksAdapter
    private lateinit var emptyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        bookmarksViewModel =
            ViewModelProvider(this, viewModelFactory).get(BookmarksViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        emptyView = binding.emptyView
        setHasOptionsMenu(true)


        val bookmarksRv = binding.bookmarksRv
        bookmarksRv.layoutManager = LinearLayoutManager(context)
        bookmarksAdapter = BookmarksAdapter(this)
        bookmarksRv.adapter = bookmarksAdapter

        bookmarksViewModel.getAllBookmarkedMovies()
            ?.observe(this, Observer {
                setBookmarks(it)

            })
        return binding.root
    }

    private fun setBookmarks(it: List<Movie>) {
        if (it.isEmpty()) {
            emptyView.visibility = View.VISIBLE
        } else {
            bookmarksAdapter.setBookmarks(it)
        }
    }

    override fun onItemClicked(movieId: Long, movieTitle: String) {
        val navController = NavHostFragment.findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_MOVIE, movieId)
        bundle.putString(ARG_MOVIE_NAME, movieTitle)
        navController.navigate(R.id.action_bookmarksFragment_to_detailFragment, bundle)
    }

}
