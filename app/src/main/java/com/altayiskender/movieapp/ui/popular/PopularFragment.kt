package com.altayiskender.movieapp.ui.popular

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.R.id.*
import com.altayiskender.movieapp.databinding.FragmentPopularBinding
import com.altayiskender.movieapp.ui.details.DetailFragment.Companion.ARG_MOVIE
import com.altayiskender.movieapp.ui.details.DetailFragment.Companion.ARG_MOVIE_NAME
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val SORT_POPULAR = 0
private const val SORT_UPCOMING = 1
private const val SORT_PLAYING = 2

@AndroidEntryPoint
class PopularFragment : Fragment(), PopularAdapter.OnInteractionListener {

    private val popularViewModel: PopularViewModel by viewModels()
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.i("onCreateView")

        // Inflate the layout for this fragment
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        val view = binding.root


        val errorView = binding.errorView
        val tryAgainButton: Button = binding.btnTryAgain
        tryAgainButton.setOnClickListener { fetchMoviesAgain() }
        val popularAdapter = PopularAdapter(this)

        val popularRecyclerView = binding.popularRecyclerView
        popularRecyclerView.apply {
            adapter = popularAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)

        }

        popularViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer { it ->
            Timber.i("popularViewModel.moviesLiveData has ${it.size}")

            it?.let {
                binding.popularProgressBar.visibility = View.GONE
                errorView.visibility = View.GONE
                popularAdapter.setMovies(it)
                popularRecyclerView.scheduleLayoutAnimation()
            }
        })

        popularViewModel.hasError.observe(viewLifecycleOwner, Observer {
            Timber.i("popularViewModel.hasError $it.toString()")

            if (it) {
                binding.popularProgressBar.visibility = View.GONE
                errorView.apply {
                    visibility = View.VISIBLE
                }
            }


        })

        getSortByStringAndSetTitle(popularViewModel.sortBy)


        setHasOptionsMenu(true)

        return view
    }

    private fun fetchMoviesAgain() {
        binding.popularProgressBar.visibility = View.VISIBLE
        binding.errorView.visibility = View.GONE
        popularViewModel.getHomepageMovies()

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (popularViewModel.sortBy) {
            SORT_POPULAR -> menu.findItem(popularItem)?.isEnabled = false
            SORT_UPCOMING -> menu.findItem(upcomingItem)?.isEnabled = false
            SORT_PLAYING -> menu.findItem(nowPlayingItem)?.isEnabled = false
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            popularItem -> {
                loadMoviesWhenClickingSortByMenuItem(SORT_POPULAR)
                true
            }
            upcomingItem -> {
                loadMoviesWhenClickingSortByMenuItem(SORT_UPCOMING)
                true
            }
            nowPlayingItem -> {
                loadMoviesWhenClickingSortByMenuItem(SORT_PLAYING)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun loadMoviesWhenClickingSortByMenuItem(sortBy: Int) {
        popularViewModel.sortBy = sortBy
        popularViewModel.getHomepageMovies()
        getSortByStringAndSetTitle(sortBy)
        activity?.invalidateOptionsMenu()
    }

    private fun getSortByStringAndSetTitle(sortBy: Int) {
        val str = when (sortBy) {
            SORT_POPULAR -> requireContext().resources.getString(R.string.popular)
            SORT_PLAYING -> requireContext().resources.getString(R.string.now_playing)
            SORT_UPCOMING -> requireContext().resources.getString(R.string.upcoming)
            else -> null
        }
        (activity as? AppCompatActivity)?.supportActionBar?.title = str
    }

    @SuppressLint("CheckResult")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_popular, menu)

        val searchMenuItem = menu.findItem(searchItem)
        val searchView = searchMenuItem?.actionView as SearchView

        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                popularViewModel.getHomepageMovies()
                return true
            }

        })


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (query.trim().length > 2) {
                        popularViewModel.searchMovie(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }
        })
    }


    override fun onItemClicked(movieId: Long, movieTitle: String) {
        val navController = findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_MOVIE, movieId)
        bundle.putString(ARG_MOVIE_NAME, movieTitle)
        navController.navigate(action_popularFragment_to_detailFragment, bundle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






