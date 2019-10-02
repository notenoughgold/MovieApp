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
import com.altayiskender.movieapp.models.Movie
import com.altayiskender.movieapp.ui.popular.ARG_MOVIE
import com.altayiskender.movieapp.ui.popular.ARG_MOVIE_NAME
import kotlinx.android.synthetic.main.fragment_bookmarks.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber


class BookmarksFragment : Fragment(), KodeinAware, BookmarksAdapter.OnInteractionListener {
    override val kodein by kodein()

    private val viewModelFactory: BookmarksViewModelFactory by instance()
    lateinit var bookmarksViewModel: BookmarksViewModel

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
        Timber.i("onCreateView")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmarks, container, false)
        var emptyView = view.empty_view
        setHasOptionsMenu(true)
        val bookmarksAdapter = BookmarksAdapter(layoutInflater, this)
        val bookmarksRv = view.bookmarksRv
        bookmarksRv.layoutManager = LinearLayoutManager(context)
        bookmarksRv.adapter = bookmarksAdapter

        bookmarksViewModel.getAllBookmarkedMovies()
            ?.observe(viewLifecycleOwner, Observer {
                bookmarksAdapter.setBookmarks(it)

                if (it.isEmpty()) {
                    emptyView.visibility = View.VISIBLE
                } else {
                    emptyView.visibility = View.GONE
                }
            })
        return view
    }


    override fun onItemClicked(movieId: Long, movieTitle: String) {
        val navController = NavHostFragment.findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_MOVIE, movieId)
        bundle.putString(ARG_MOVIE_NAME, movieTitle)
        navController.navigate(R.id.action_bookmarksFragment_to_detailFragment, bundle)
    }

}
