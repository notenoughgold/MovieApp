package com.altayiskender.movieapp.ui.details


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.R.id.bookmarkItem
import com.altayiskender.movieapp.R.id.removeBookmarkItem
import kotlinx.android.synthetic.main.fragment_detail.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

private const val ARG_MOVIE = "arg_movie"
private const val ARG_PEOPLE = "arg_people"
private const val ARG_MOVIE_NAME = "arg_movie_name"
private const val ARG_PEOPLE_NAME = "arg_people_name"


class DetailFragment : Fragment(), KodeinAware, DetailAdapter.OnInteractionListener {

    override val kodein by kodein()


    private val viewModelFactory: DetailsViewModelFactory by instance()
    lateinit var detailsViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        detailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        setHasOptionsMenu(true)

        if (detailsViewModel.movieId == null && detailsViewModel.movieTitle == null) {
            detailsViewModel.movieId = arguments!!.getLong(ARG_MOVIE)
            detailsViewModel.movieTitle = arguments!!.getString(ARG_MOVIE_NAME)
        }


        val detailsRv = view.detailsRv
        detailsRv.layoutManager = LinearLayoutManager(context)
        detailsRv.adapter = DetailAdapter(layoutInflater, this)

        detailsViewModel.getMovieDetails()
            ?.observe(viewLifecycleOwner, Observer {
                (detailsRv.adapter as DetailAdapter).setMovie(it)
            })
        (activity as? AppCompatActivity)?.supportActionBar?.title = detailsViewModel.movieTitle

        detailsViewModel.checkIfMovieSaved(detailsViewModel.movieId as Long)
            ?.observe(viewLifecycleOwner, Observer {
                it?.let { activity?.invalidateOptionsMenu() }
            })

        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        when (detailsViewModel.movieSavedStatusLiveData.value) {
            true -> menu.findItem(removeBookmarkItem)?.isVisible = true
            false -> menu.findItem(bookmarkItem)?.isVisible = true
        }
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
                detailsViewModel.saveMovieToBookmarks()
                Toast.makeText(context, R.string.movie_saved, Toast.LENGTH_SHORT).show()
                true
            }
            removeBookmarkItem -> {
                detailsViewModel.deleteMovieFromBookmarks()
                Toast.makeText(context, R.string.movie_unsaved, Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }


    }
}
