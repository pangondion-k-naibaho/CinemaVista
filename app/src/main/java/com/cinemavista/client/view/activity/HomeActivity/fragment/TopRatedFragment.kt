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
import com.cinemavista.client.databinding.FragmentTopRatedBinding
import com.cinemavista.client.model.data_class.response.MovieInformation
import com.cinemavista.client.view.activity.DetailActivity.DetailActivity
import com.cinemavista.client.view.activity.HomeActivity.HomeCommunicator
import com.cinemavista.client.view.adapter.ItemMovieAdapter
import com.cinemavista.client.view.advanced_ui.PopUpDialogListener
import com.cinemavista.client.view.advanced_ui.showPopUpDialog
import com.cinemavista.client.viewmodel.HomeViewModel

class TopRatedFragment : Fragment() {
    private val TAG = TopRatedFragment::class.java.simpleName
    private var _binding: FragmentTopRatedBinding?= null
    private val binding get() = _binding!!
    private var input: String? = ""
    private lateinit var homeCommunicator: HomeCommunicator
    private val homeViewModel by viewModels<HomeViewModel>()
    private var currentPage: Int?= null

    companion object{
        const val DELIVERED_INPUT = "DELIVERED_INPUT"
        fun newInstance(input: String): TopRatedFragment{
            val fragment = TopRatedFragment()
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
        _binding = FragmentTopRatedBinding.inflate(layoutInflater, container, false)
        homeCommunicator = activity as HomeCommunicator
        initView()
        return binding.root
    }

    private fun initView(){
        currentPage = 1
        homeViewModel.getTopRatedMovies(page = currentPage!!)

        homeViewModel.isLoading.observe(this@TopRatedFragment.requireActivity(), {
            if(it) homeCommunicator.onStartLoading() else homeCommunicator.onStopLoading()
        })

        homeViewModel.isFail.observe(this@TopRatedFragment.requireActivity(), {
            if(it){
                this@TopRatedFragment.requireActivity().showPopUpDialog(
                    getString(R.string.tvPopUpDescription),
                    R.drawable.sadness,
                    object: PopUpDialogListener {
                        override fun onClickListener() {
                            this@TopRatedFragment.requireActivity().recreate()
                            this@TopRatedFragment.requireActivity().closeOptionsMenu()
                        }
                    }
                )
            }
        })

        homeViewModel.topRatedMovies.observe(this@TopRatedFragment.requireActivity(), {listTopRatedMovie->
            Log.d(TAG, "List Top Rated movies: ${listTopRatedMovie}")

            binding.rvListMovie.apply {

                val movieAdapter = ItemMovieAdapter(
                    listTopRatedMovie.results!!.toMutableList(),
                    object: ItemMovieAdapter.ItemListener{
                        override fun onItemClicked(item: MovieInformation) {
                            startActivity(
                                DetailActivity.newIntent(
                                    this@TopRatedFragment.requireActivity(),
                                    item
                                )
                            )
                            this@TopRatedFragment.requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                )

                val rvLayoutManager = GridLayoutManager(this@TopRatedFragment.requireActivity(), 2)

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
                            && totalItemCount >= listTopRatedMovie.results!!.size
                        ){
                            currentPage = currentPage?.plus(1)
                            homeViewModel.getTopRatedMoviesMore(page = currentPage)
                            homeViewModel.nowPlayingMovies2.observe(this@TopRatedFragment.requireActivity(), {neoListTopRatedMovie->
                                if(!neoListTopRatedMovie.results.isNullOrEmpty()){
                                    movieAdapter.addItem(neoListTopRatedMovie.results!!)
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