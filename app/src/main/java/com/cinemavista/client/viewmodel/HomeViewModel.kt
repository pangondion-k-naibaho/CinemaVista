package com.cinemavista.client.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cinemavista.client.model.data_class.request.DiscoverMovie
import com.cinemavista.client.model.data_class.response.MovieCollection
import com.cinemavista.client.model.data_class.response.MovieInformation
import com.cinemavista.client.model.remote.ApiConfig
import com.cinemavista.client.view.activity.HomeActivity.HomeCommunicator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    private var _nowPlayingMovies = MutableLiveData<MovieCollection>()
    val nowPlayingMovies : LiveData<MovieCollection> = _nowPlayingMovies

    private var _popularMovies = MutableLiveData<MovieCollection>()
    val popularMovies: LiveData<MovieCollection> = _popularMovies

    private var _topRatedMovies = MutableLiveData<MovieCollection>()
    val topRatedMovies: LiveData<MovieCollection> = _topRatedMovies

    private var _upcomingMovies = MutableLiveData<MovieCollection>()
    val upcomingMovies: LiveData<MovieCollection> = _upcomingMovies

    private var _discoveredMovies = MutableLiveData<MovieCollection>()
    val discoveredMovies: LiveData<MovieCollection> = _discoveredMovies

    fun getDiscoveredMoviesBasedOnGenre(page: Int, genres: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDiscoveredMovie(page = page, withGenres = genres).enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _discoveredMovies.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getPopularMovies(language: String?= null, page: Int, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMoviePopular(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _popularMovies.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getNowPlayingMovies(language: String?= null, page: Int, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovieNowPlaying(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _nowPlayingMovies.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getTopRatedMovies(language: String?= null, page: Int, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovieTopRated(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _topRatedMovies.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getUpcomingMovies(language: String?= null, page: Int, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovieUpcoming(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _upcomingMovies.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }


}