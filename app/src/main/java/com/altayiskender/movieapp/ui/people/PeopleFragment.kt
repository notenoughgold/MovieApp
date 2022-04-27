package com.altayiskender.movieapp.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.altayiskender.movieapp.R
import com.altayiskender.movieapp.databinding.FragmentPeopleBinding
import com.altayiskender.movieapp.models.PeopleResponse
import com.altayiskender.movieapp.ui.details.DetailFragment.Companion.ARG_MOVIE
import com.altayiskender.movieapp.ui.details.DetailFragment.Companion.ARG_MOVIE_NAME
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PeopleFragment : Fragment(), PeopleAdapter.OnInteractionListener {

    private val peopleViewModel: PeopleViewModel by viewModels()
    private lateinit var peopleAdapter: PeopleAdapter
    private var _binding: FragmentPeopleBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        if (peopleViewModel.peopleId == null && peopleViewModel.peopleName == null) {
            peopleViewModel.peopleId = requireArguments().getLong(ARG_PEOPLE)
            peopleViewModel.peopleName = requireArguments().getString(ARG_PEOPLE_NAME)
        }

        val peopleRv = binding.peopleRv
        peopleAdapter = PeopleAdapter(layoutInflater, this)
        peopleRv.layoutManager = LinearLayoutManager(context)
        peopleRv.adapter = peopleAdapter

        peopleViewModel.getPeopleDetails()
            ?.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                setPeopleDetails(it)

            })
        (activity as? AppCompatActivity)?.supportActionBar?.title = peopleViewModel.peopleName

        return view
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_PEOPLE_NAME = "arg_people_name"
        const val ARG_PEOPLE = "arg_people"
    }
}
