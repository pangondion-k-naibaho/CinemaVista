package com.cinemavista.client.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cinemavista.client.model.data_class.response.MovieCollection
import com.cinemavista.client.model.remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

    private var _nowPlayingMovies = MutableLiveData<MovieCollection>()
    val nowPlayingMovies : LiveData<MovieCollection> = _nowPlayingMovies

    private var _nowPlayingMovies2 = MutableLiveData<MovieCollection>()
    val nowPlayingMovies2 : LiveData<MovieCollection> = _nowPlayingMovies2

    private var _popularMovies = MutableLiveData<MovieCollection>()
    val popularMovies: LiveData<MovieCollection> = _popularMovies

    private var _popularMovies2 = MutableLiveData<MovieCollection>()
    val popularMovies2: LiveData<MovieCollection> = _popularMovies2

    private var _topRatedMovies = MutableLiveData<MovieCollection>()
    val topRatedMovies: LiveData<MovieCollection> = _topRatedMovies

    private var _topRatedMovies2 = MutableLiveData<MovieCollection>()
    val topRatedMovies2: LiveData<MovieCollection> = _topRatedMovies2

    private var _upcomingMovies = MutableLiveData<MovieCollection>()
    val upcomingMovies: LiveData<MovieCollection> = _upcomingMovies

    private var _upcomingMovies2 = MutableLiveData<MovieCollection>()
    val upcomingMovies2: LiveData<MovieCollection> = _upcomingMovies2

    private var _discoveredMovies = MutableLiveData<MovieCollection>()
    val discoveredMovies: LiveData<MovieCollection> = _discoveredMovies

    private var _discoveredMovies2 = MutableLiveData<MovieCollection>()
    val discoveredMovies2: LiveData<MovieCollection> = _discoveredMovies2

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

    fun getDiscoveredMoviesBasedOnGenreMore(page: Int?, genres: String?){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDiscoveredMovie(page = page, withGenres = genres)

        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _discoveredMovies2.value = response.body()
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

    fun getPopularMoviesMore(language: String?= null, page: Int?, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMoviePopular(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _popularMovies2.value = response.body()
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

    fun getNowPlayingMoviesMore(language: String?= null, page: Int?, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovieNowPlaying(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _nowPlayingMovies2.value = response.body()
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

    fun getTopRatedMoviesMore(language: String?= null, page: Int?, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovieTopRated(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _topRatedMovies2.value = response.body()
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

    fun getUpcomingMoviesMore(language: String?= null, page: Int?, region: String?=null){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovieUpcoming(language, page, region)
        client.enqueue(object: retrofit2.Callback<MovieCollection>{
            override fun onResponse(
                call: Call<MovieCollection>,
                response: Response<MovieCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _upcomingMovies2.value = response.body()
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