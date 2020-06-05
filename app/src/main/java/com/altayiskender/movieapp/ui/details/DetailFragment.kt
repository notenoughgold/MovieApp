package com.altayiskender.movieapp.ui.details


import android.content.Context
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
import com.altayiskender.movieapp.databinding.FragmentDetailBinding
import com.altayiskender.movieapp.utils.getBackdropUrl
import com.altayiskender.movieapp.utils.loadImage
import com.google.android.material.chip.Chip
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject

private const val ARG_MOVIE = "arg_movie"
private const val ARG_PEOPLE = "arg_people"
private const val ARG_MOVIE_NAME = "arg_movie_name"
private const val ARG_PEOPLE_NAME = "arg_people_name"


class DetailFragment : Fragment(), CastListAdapter.OnInteractionListener,
    CrewListAdapter.OnInteractionListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var detailsViewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

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
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        if (detailsViewModel.movieId == null && detailsViewModel.movieTitle == null) {
                detailsViewModel.movieId = requireArguments().getLong(ARG_MOVIE)
                detailsViewModel.movieTitle = requireArguments().getString(ARG_MOVIE_NAME)
            }

        (activity as? AppCompatActivity)?.supportActionBar?.title = detailsViewModel.movieTitle

        val castListRv = binding.castListRv
        val crewListRv = binding.crewListRv
        castListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        crewListRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        castListRv.adapter = CastListAdapter(this)
        crewListRv.adapter = CrewListAdapter(this)

        detailsViewModel.getMovieDetails()
            ?.observe(viewLifecycleOwner, Observer { movie ->
                if (movie.backdropPath?.isNotEmpty() == true) {
                    binding.detailsImageView.loadImage(getBackdropUrl(movie.backdropPath))
                }

                if (movie.genres?.isNotEmpty() == true) {
                    binding.detailsGenresCg.apply {
                        visibility = View.VISIBLE
                        if (this.childCount == 0) {
                            val genresList = movie.genres!!.map { it.name }
                            genresList.forEach { str ->
                                val chip = Chip(binding.detailsGenresCg.context)
                                chip.text = str
                                chip.isCheckable = false
                                chip.isClickable = false
                                binding.detailsGenresCg.addView(chip)
                            }
                        }
                    }
                } else {
                    binding.detailsGenresCg.visibility = View.GONE
                }

                if (movie.overview?.isEmpty() == true) {
                    binding.detailsDescriptionTv.visibility = View.GONE
                } else {
                    binding.detailsDescriptionTv.visibility = View.VISIBLE
                    binding.detailsDescriptionTv.text = movie.overview
                }

                binding.detailsReleaseTv.text = movie.releaseDate

                binding.detailsRatingTv.text = "${movie.voteAverage} / 10"

                binding.detailsRuntimeTv.text = "${movie.runtime} m"

                (castListRv.adapter as CastListAdapter).submitList(movie.credits?.cast)
                (crewListRv.adapter as CrewListAdapter).submitList(movie.credits?.crew)

            })

        detailsViewModel.checkIfMovieSaved(detailsViewModel.movieId as Long)
            ?.observe(viewLifecycleOwner, Observer {
                it?.let { activity?.invalidateOptionsMenu() }
            })

        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
