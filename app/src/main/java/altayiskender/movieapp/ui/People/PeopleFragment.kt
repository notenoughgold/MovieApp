package altayiskender.movieapp.ui.People


import altayiskender.movieapp.Pojos.PeopleResponse
import altayiskender.movieapp.R
import altayiskender.movieapp.databinding.FragmentPeopleBinding
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


private const val ARG_PEOPLE = "arg_people"
private const val ARG_MOVIE = "arg_movie"
private const val ARG_PEOPLE_NAME = "arg_people_name"
private const val ARG_MOVIE_NAME = "arg_movie_name"


class PeopleFragment : Fragment(), PeopleAdapter.OnInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var peopleViewModel: PeopleViewModel? = null
    private var peopleAdapter: PeopleAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentPeopleBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        peopleViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(PeopleViewModel::class.java)

        if (peopleViewModel?.peopleId == null && peopleViewModel?.peopleName == null) {
            peopleViewModel?.peopleId = arguments!!.getLong(ARG_PEOPLE)
            peopleViewModel?.peopleName = arguments!!.getString(ARG_PEOPLE_NAME)
        }
        if (peopleAdapter == null) {
            peopleAdapter = PeopleAdapter(this)

        }
        val peopleRv = binding.peopleRv
        peopleRv.layoutManager = LinearLayoutManager(context)
        peopleRv.adapter = peopleAdapter

        peopleViewModel?.getPeopleDetails()
                ?.observe(this, androidx.lifecycle.Observer {
                        setPeopleDetails(it)

                })
        (activity as? AppCompatActivity)?.supportActionBar?.title = peopleViewModel?.peopleName

        return binding.root
    }

    private fun setPeopleDetails(peopleResponse: PeopleResponse) {

        peopleAdapter?.setPeople(peopleResponse)
    }

    override fun onItemClicked(castId: Long, castTitle: String) {
        val navController = findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_MOVIE, castId)
        bundle.putString(ARG_MOVIE_NAME, castTitle)
        navController.navigate(R.id.action_peopleFragment_to_detailFragment, bundle)
    }

}
