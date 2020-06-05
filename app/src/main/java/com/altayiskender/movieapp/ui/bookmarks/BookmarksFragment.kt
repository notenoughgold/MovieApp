package com.altayiskender.movieapp.ui.bookmarks


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.databinding.FragmentBookmarksBinding
import com.altayiskender.movieapp.ui.popular.ARG_MOVIE
import com.altayiskender.movieapp.ui.popular.ARG_MOVIE_NAME
import dagger.android.support.AndroidSupportInjection

import timber.log.Timber
import javax.inject.Inject

class BookmarksFragment : Fragment(), BookmarksAdapter.OnInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var bookmarksViewModel: BookmarksViewModel

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

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
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        val bookmarksAdapter = BookmarksAdapter(this)
        val bookmarksRv = binding.bookmarksRv
        bookmarksRv.layoutManager = LinearLayoutManager(context)
        bookmarksRv.adapter = bookmarksAdapter

        bookmarksViewModel.getAllBookmarkedMovies()
            ?.observe(viewLifecycleOwner, Observer {
                bookmarksAdapter.setBookmarks(it)

                if (it.isEmpty()) {
                    binding.emptyView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.GONE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
