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
import com.cinemavista.client.R
import com.cinemavista.client.databinding.FragmentNowPlayingBinding
import com.cinemavista.client.model.data_class.response.MovieInformation
import com.cinemavista.client.view.activity.HomeActivity.HomeCommunicator
import com.cinemavista.client.view.adapter.ItemMovieAdapter
import com.cinemavista.client.viewmodel.HomeViewModel

class NowPlayingFragment : Fragment() {
    private val TAG = NowPlayingFragment::class.java.simpleName
    private var _binding: FragmentNowPlayingBinding?= null
    private val binding get() = _binding!!
    private var input: String? = ""
    private lateinit var homeCommunicator: HomeCommunicator
    private val homeViewModel by viewModels<HomeViewModel>()

    companion object {
        const val DELIVERED_INPUT = "DELIVERED_INPUT"
        fun newInstance(input: String): NowPlayingFragment{
            val fragment = NowPlayingFragment()
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
        _binding = FragmentNowPlayingBinding.inflate(layoutInflater, container, false)
        homeCommunicator = activity as HomeCommunicator
        initView()

        return binding.root
    }

    private fun initView(){
        homeViewModel.getNowPlayingMovies(page = 1)

        homeViewModel.isLoading.observe(this@NowPlayingFragment.requireActivity(), {
            if(it) homeCommunicator.onStartLoading() else homeCommunicator.onStopLoading()
        })

        homeViewModel.isFail.observe(this@NowPlayingFragment.requireActivity(), {
            Log.d(TAG, "isLoadMovieFail: ${it}")
        })

        homeViewModel.nowPlayingMovies.observe(this@NowPlayingFragment.requireActivity(), {listNowPlayingMovie->
            Log.d(TAG, "List now playing movies : ${listNowPlayingMovie}")
            binding.rvListMovie.apply {

                val movieAdapter = ItemMovieAdapter(
                    listNowPlayingMovie.results!!.toMutableList(),
                    object: ItemMovieAdapter.ItemListener{
                        override fun onItemClicked(item: MovieInformation) {
                            Toast.makeText(this@NowPlayingFragment.requireActivity(), "Movie clicked: ${item.title}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                val rvLayoutManager = GridLayoutManager(this@NowPlayingFragment.requireActivity(), 2)

                adapter = movieAdapter
                layoutManager = rvLayoutManager
            }
        })
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        input = null
    }
}