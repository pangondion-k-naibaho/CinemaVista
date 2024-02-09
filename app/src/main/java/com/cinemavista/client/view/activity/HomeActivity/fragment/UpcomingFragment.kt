package com.cinemavista.client.view.activity.HomeActivity.fragment

import android.content.ClipData.Item
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
import com.cinemavista.client.databinding.FragmentUpcomingBinding
import com.cinemavista.client.model.data_class.response.MovieInformation
import com.cinemavista.client.view.activity.HomeActivity.HomeCommunicator
import com.cinemavista.client.view.adapter.ItemMovieAdapter
import com.cinemavista.client.viewmodel.HomeViewModel

class UpcomingFragment : Fragment() {
    private val TAG = UpcomingFragment::class.java.simpleName
    private var _binding: FragmentUpcomingBinding?= null
    private val binding get() = _binding!!
    private var input: String? = ""
    private lateinit var homeCommunicator: HomeCommunicator
    private val homeViewModel by viewModels<HomeViewModel>()
    private var currentPage: Int?= null

    companion object{
        const val DELIVERED_INPUT = "DELIVERED_INPUT"

        fun newInstance(input: String): UpcomingFragment{
            val fragment = UpcomingFragment()
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
        _binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        homeCommunicator = activity as HomeCommunicator
        initView()
        return binding.root
    }

    private fun initView(){
        currentPage = 1
        homeViewModel.getUpcomingMovies(page = currentPage!!)

        homeViewModel.isLoading.observe(this@UpcomingFragment.requireActivity(), {
            if(it) homeCommunicator.onStartLoading() else homeCommunicator.onStopLoading()
        })

        homeViewModel.isFail.observe(this@UpcomingFragment.requireActivity(), {
            Log.d(TAG, "isLoadMovieFail: ${it}")
        })

        homeViewModel.upcomingMovies.observe(this@UpcomingFragment.requireActivity(), {listUpcomingMovies->
            Log.d(TAG, "List upcoming movies: ${listUpcomingMovies}")

            binding.rvListMovie.apply {

                val movieAdapter = ItemMovieAdapter(
                    listUpcomingMovies.results!!.toMutableList(),
                    object: ItemMovieAdapter.ItemListener{
                        override fun onItemClicked(item: MovieInformation) {
                            Toast.makeText(this@UpcomingFragment.requireActivity(), "Movie clicked : ${item.title}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                val rvLayoutManager = GridLayoutManager(this@UpcomingFragment.requireActivity(), 2)

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
                            && totalItemCount >= listUpcomingMovies.results!!.size
                        ){
                            currentPage = currentPage?.plus(1)
                            homeViewModel.getUpcomingMoviesMore(page = currentPage)
                            homeViewModel.upcomingMovies2.observe(this@UpcomingFragment.requireActivity(), {neoListUpcomingMovie->
                                if(!neoListUpcomingMovie.results.isNullOrEmpty()){
                                    movieAdapter.addItem(neoListUpcomingMovie.results!!)
                                }
                            })
                        }

                    }
                })
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        input = null
    }

}