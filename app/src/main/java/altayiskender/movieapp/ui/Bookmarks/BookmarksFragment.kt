package altayiskender.movieapp.ui.Bookmarks


import altayiskender.movieapp.Pojos.Movie
import altayiskender.movieapp.R
import altayiskender.movieapp.databinding.FragmentBookmarksBinding
import altayiskender.movieapp.ui.Popular.ARG_MOVIE
import altayiskender.movieapp.ui.Popular.ARG_MOVIE_NAME
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class BookmarksFragment : Fragment(),BookmarksAdapter.OnInteractionListener {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var bookmarksViewModel: BookmarksViewModel? = null
    private var bookmarksAdapter: BookmarksAdapter? = null
    private var emptyView:TextView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        emptyView = binding.emptyView
        setHasOptionsMenu(true)

        bookmarksViewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(BookmarksViewModel::class.java)

        if (bookmarksAdapter == null) {
            bookmarksAdapter = BookmarksAdapter(this)
        }
        val bookmarksRv = binding.bookmarksRv
        bookmarksRv.layoutManager = LinearLayoutManager(context)
        bookmarksRv.adapter = bookmarksAdapter

        bookmarksViewModel?.getAllBookmarkedMovies()
                ?.observe(this, Observer  {
                    it?.let { it ->
                        setBookmarks(it)
                    }
                })
        return binding.root
    }

    private fun setBookmarks(it: List<Movie>) {
        if (it.isEmpty()){
            emptyView?.visibility = View.VISIBLE
        }else{
            bookmarksAdapter?.setBookmarks(it)
        }
    }
    override fun onItemClicked(movieId: Long, movieTitle: String) {
        val navController = NavHostFragment.findNavController(this)
        val bundle = Bundle()
        bundle.putLong(ARG_MOVIE, movieId)
        bundle.putString(ARG_MOVIE_NAME, movieTitle)
        navController.navigate(R.id.action_bookmarksFragment_to_detailFragment, bundle)
    }

}
