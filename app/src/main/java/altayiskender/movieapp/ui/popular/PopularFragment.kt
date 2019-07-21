package altayiskender.movieapp.ui.popular


import altayiskender.movieapp.R
import altayiskender.movieapp.R.id.*
import altayiskender.movieapp.databinding.FragmentPopularBinding
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.consumeEach
import javax.inject.Inject


const val ARG_MOVIE = "arg_movie"
const val ARG_MOVIE_NAME = "arg_movie_name"

private const val SORT_POPULAR = 0
private const val SORT_UPCOMING = 1
private const val SORT_PLAYING = 2

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class PopularFragment : Fragment(), PopularAdapter.OnInteractionListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var popularViewModel: PopularViewModel? = null
    private var popularAdapter: PopularAdapter? = null
    private lateinit var popularRecyclerView: RecyclerView
    private lateinit var errorView: LinearLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = FragmentPopularBinding.inflate(inflater, container, false)

        popularViewModel = ViewModelProviders.of(this, viewModelFactory).get(PopularViewModel::class.java)

        popularAdapter = PopularAdapter(layoutInflater, this)
        errorView = binding.errorView
        val tryAgainButton: Button = binding.btnTryAgain
        tryAgainButton.setOnClickListener { fetchMoviesAgain() }




        popularRecyclerView = binding.popularRecyclerView
        popularRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
            adapter = popularAdapter

        }

        popularViewModel?.moviesLiveData?.observe(this, Observer { it ->
            it?.let {
                popularProgressBar.visibility = View.GONE
                errorView.visibility = View.GONE
                popularAdapter?.setMovies(it)
                popularRecyclerView.scheduleLayoutAnimation()
            }
        })

        popularViewModel?.hasError?.observe(this, Observer {
            if (it) {
                popularProgressBar.visibility = View.GONE
                errorView.apply {
                    visibility = View.VISIBLE
                }
            }


        })

        popularViewModel?.getHomepageMovies()


        getSortByStringAndSetTitle(popularViewModel!!.sortBy)


        setHasOptionsMenu(true)

        return binding.root
    }

    private fun fetchMoviesAgain() {
        popularProgressBar.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        popularViewModel?.getHomepageMovies()

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (popularViewModel?.sortBy) {
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
        popularViewModel?.sortBy = sortBy
        popularViewModel?.getHomepageMovies()
        getSortByStringAndSetTitle(sortBy)
        activity?.invalidateOptionsMenu()
    }

    private fun getSortByStringAndSetTitle(sortBy: Int) {
        val str = when (sortBy) {
            SORT_POPULAR -> context!!.resources.getString(R.string.popular)
            SORT_PLAYING -> context!!.resources.getString(R.string.now_playing)
            SORT_UPCOMING -> context!!.resources.getString(R.string.upcoming)
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
                popularViewModel?.getHomepageMovies()
                return true
            }

        })
        val channel = BroadcastChannel<String>(1)
        CoroutineScope(Dispatchers.IO).launch {
            channel.consumeEach {

                delay(1000)
                popularViewModel?.searchMovie(it)

            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    if (newText.trim().length > 2) {
                        CoroutineScope(Dispatchers.IO).launch {
                            channel.send(it)


                        }
                    }

                }
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
}





