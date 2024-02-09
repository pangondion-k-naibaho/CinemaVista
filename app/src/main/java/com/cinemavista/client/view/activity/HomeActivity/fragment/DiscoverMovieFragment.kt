package com.cinemavista.client.view.activity.HomeActivity.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cinemavista.client.R
import com.cinemavista.client.databinding.FragmentDiscoverMovieBinding
import com.cinemavista.client.model.data_class.response.MovieInformation
import com.cinemavista.client.view.activity.HomeActivity.HomeCommunicator
import com.cinemavista.client.view.adapter.ItemMovieAdapter
import com.cinemavista.client.view.advanced_ui.DiscoverChipGroupView
import com.cinemavista.client.viewmodel.HomeViewModel

class DiscoverMovieFragment : Fragment() {
    private val TAG = DiscoverMovieFragment::class.java.simpleName
    private var _binding: FragmentDiscoverMovieBinding?= null
    private val binding get() = _binding!!
    private var input: String? = ""
    private lateinit var homeCommunicator: HomeCommunicator
    private val homeViewModel by viewModels<HomeViewModel>()
    private var currentPage: Int? = null
    private var currentGenres: String?= null

    companion object{
        const val DELIVERED_INPUT = "DELIVERED_INPUT"
        fun newInstance(input: String): DiscoverMovieFragment{
            val fragment = DiscoverMovieFragment()
            fragment.input = input

            val bundle = Bundle()
            bundle.putString(DELIVERED_INPUT, input)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoverMovieBinding.inflate(layoutInflater, container, false)
        homeCommunicator = activity as HomeCommunicator
        initView()

        return binding.root
    }

    private fun initView(){
        currentPage = 1
        homeViewModel.getDiscoveredMoviesBasedOnGenre(page = currentPage!!, genres = currentGenres)

        homeViewModel.isLoading.observe(this@DiscoverMovieFragment.requireActivity(), {
            if(it) homeCommunicator.onStartLoading() else homeCommunicator.onStopLoading()
        })

        homeViewModel.isFail.observe(this@DiscoverMovieFragment.requireActivity(), {
            Log.d(TAG, "isLoadMovieFail: ${it}")
        })

        homeViewModel.discoveredMovies.observe(this@DiscoverMovieFragment.requireActivity(), {listDiscoveredMovie->
            Log.d(TAG, "List Discovered Movies: ${listDiscoveredMovie}")

            binding.rvListMovie.apply {
                val movieAdapter = ItemMovieAdapter(
                    listDiscoveredMovie.results!!.toMutableList(),
                    object: ItemMovieAdapter.ItemListener{
                        override fun onItemClicked(item: MovieInformation) {
                            Toast.makeText(this@DiscoverMovieFragment.requireActivity(), "Movie Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                val rvLayoutManager = GridLayoutManager(this@DiscoverMovieFragment.requireActivity(), 2)

                adapter = movieAdapter
                layoutManager = rvLayoutManager

                addOnScrollListener(object: RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)

                        val visibleItemCount = rvLayoutManager.childCount
                        val totalItemCount = rvLayoutManager.itemCount
                        val firstVisibleItemPosition = rvLayoutManager.findFirstVisibleItemPosition()

                        if((visibleItemCount+firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= listDiscoveredMovie.results!!.size
                        ){
                            currentPage = currentPage?.plus(1)
                            homeViewModel.getDiscoveredMoviesBasedOnGenreMore(page = currentPage, genres = currentGenres)
                            homeViewModel.discoveredMovies2.observe(this@DiscoverMovieFragment.requireActivity(), {neoListDiscoveredMovie->
                                if(!neoListDiscoveredMovie.results.isNullOrEmpty()){
                                    movieAdapter.addItem(neoListDiscoveredMovie.results!!)
                                }
                            })
                        }

                    }
                })
            }
        })

        binding.discoverMovieWidget.apply {
            setInvisible()
            setListener(object: DiscoverChipGroupView.DCGVListener{
                override fun onButtonVisibilityClicked() {
                    if(isInvisible()) setVisible() else setInvisible()
                }

                override fun onItemChipGroupClicked() {}

                override fun onButtonDiscoverBasedOnGenresClicked() {
                    currentPage = 1
                    if(getData().isEmpty()){
//                        Toast.makeText(this@DiscoverMovieFragment.requireActivity(), "null or empty", Toast.LENGTH_SHORT).show()
                        homeViewModel.getDiscoveredMoviesBasedOnGenre(page = currentPage!!, genres = null)
                    }else{
//                        Toast.makeText(this@DiscoverMovieFragment.requireActivity(), getData().joinToString(","), Toast.LENGTH_SHORT).show()
                        currentGenres = getData().joinToString("%2C")
                        homeViewModel.getDiscoveredMoviesBasedOnGenre(page = currentPage!!, genres = currentGenres)
                    }
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        input = null
    }
}