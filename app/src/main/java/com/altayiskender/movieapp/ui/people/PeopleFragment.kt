package com.altayiskender.movieapp.ui.people


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.databinding.FragmentPeopleBinding
import com.altayiskender.movieapp.models.PeopleResponse
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

private const val ARG_PEOPLE = "arg_people"
private const val ARG_MOVIE = "arg_movie"
private const val ARG_PEOPLE_NAME = "arg_people_name"
private const val ARG_MOVIE_NAME = "arg_movie_name"


class PeopleFragment : Fragment(), KodeinAware, PeopleAdapter.OnInteractionListener {
    override val kodein by kodein()

    private val viewModelFactory: PeopleViewModelFactory by instance()
    lateinit var peopleViewModel: PeopleViewModel
    private lateinit var peopleAdapter: PeopleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
        peopleViewModel = ViewModelProvider(this, viewModelFactory).get(PeopleViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentPeopleBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        if (peopleViewModel.peopleId == null && peopleViewModel.peopleName == null) {
            peopleViewModel.peopleId = arguments!!.getLong(ARG_PEOPLE)
            peopleViewModel.peopleName = arguments!!.getString(ARG_PEOPLE_NAME)
        }

        val peopleRv = binding.peopleRv
        peopleAdapter = PeopleAdapter(this)
        peopleRv.layoutManager = LinearLayoutManager(context)
        peopleRv.adapter = peopleAdapter

        peopleViewModel.getPeopleDetails()
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                setPeopleDetails(it)

            })
        (activity as? AppCompatActivity)?.supportActionBar?.title = peopleViewModel.peopleName

        return binding.root
    }

    private fun setPeopleDetails(peopleResponse: PeopleResponse) {

        peopleAdapter.setPeople(peopleResponse)
    }

    override fun onItemClicked(castId: Long, castTitle: String) {
        val navController = findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_MOVIE, castId)
        bundle.putString(ARG_MOVIE_NAME, castTitle)
        navController.navigate(R.id.action_peopleFragment_to_detailFragment, bundle)
    }

}
