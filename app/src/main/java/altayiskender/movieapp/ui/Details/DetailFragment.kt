package altayiskender.movieapp.ui.Details


import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.R
import altayiskender.movieapp.R.id.bookmarkItem
import altayiskender.movieapp.R.id.removeBookmarkItem
import altayiskender.movieapp.databinding.FragmentDetailBinding
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

private const val ARG_MOVIE = "arg_movie"
private const val ARG_PEOPLE = "arg_people"
private const val ARG_MOVIE_NAME = "arg_movie_name"
private const val ARG_PEOPLE_NAME = "arg_people_name"


class DetailFragment : Fragment(), DetailAdapter.OnInteractionListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var detailsViewModel: DetailViewModel? = null
    private var detailsAdapter: DetailAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)


        detailsViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(DetailViewModel::class.java)

        if (detailsViewModel?.movieId == null || detailsViewModel?.movieTitle == null) {
            detailsViewModel?.movieId = arguments!!.getLong(ARG_MOVIE)
            detailsViewModel?.movieTitle = arguments!!.getString(ARG_MOVIE_NAME)
        }


        if (detailsAdapter == null) {
            detailsAdapter = DetailAdapter(this)
        }
        val detailsRv = binding.detailsRv
        detailsRv.layoutManager = LinearLayoutManager(context)
        detailsRv.adapter = detailsAdapter

        detailsViewModel?.getMovieDetails()
                ?.observe(this, androidx.lifecycle.Observer {
                    it?.let { it ->
                        setMovieDetails(it)
                    }
                })
        (activity as? AppCompatActivity)?.supportActionBar?.title = detailsViewModel?.movieTitle

        detailsViewModel?.checkIfMovieSaved(detailsViewModel?.movieId as Long)?.observe(this, Observer {
            it?.let { activity?.invalidateOptionsMenu() }
        })

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (detailsViewModel != null) {
            when (detailsViewModel?.movieSavedStatusLiveData?.value) {
                true -> menu.findItem(R.id.removeBookmarkItem)?.isVisible = true
                false -> menu.findItem(R.id.bookmarkItem)?.isVisible = true
            }
        }else{
            return
        }
    }


    private fun setMovieDetails(movie: Movie) {
        detailsAdapter?.setMovie(movie)
    }

    override fun onItemClicked(castId: Long, castName: String) {
        val navController = findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_PEOPLE, castId)
        bundle.putString(ARG_PEOPLE_NAME, castName)
        navController.navigate(R.id.action_detailFragment_to_peopleFragment, bundle)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            bookmarkItem -> {
                detailsViewModel?.saveMovieToBookmarks()
                detailsViewModel?.movieSavedStatusLiveData?.value=true
                Toast.makeText(context, R.string.movie_saved, Toast.LENGTH_SHORT).show()
                true
            }
            removeBookmarkItem -> {
                detailsViewModel?.deleteMovieFromBookmarks()
                detailsViewModel?.movieSavedStatusLiveData?.value=false
                Toast.makeText(context, R.string.movie_unsaved, Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }
}
