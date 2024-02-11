package com.cinemavista.client.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cinemavista.client.model.data_class.response.MovieReviewCollection
import com.cinemavista.client.model.data_class.response.MovieVideoCollection
import com.cinemavista.client.model.data_class.response.MovieVideoInformation
import com.cinemavista.client.model.remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail : LiveData<Boolean> = _isFail

    private var _movieVideoResponse = MutableLiveData<MovieVideoCollection>()
    val movieVideoResponse: LiveData<MovieVideoCollection> = _movieVideoResponse

    private var _movieReviewCollection = MutableLiveData<MovieReviewCollection>()
    val movieReviewCollection : LiveData<MovieReviewCollection> = _movieReviewCollection

    private var _movieReviewCollection2 = MutableLiveData<MovieReviewCollection>()
    val movieReviewCollection2 : LiveData<MovieReviewCollection> = _movieReviewCollection2

    fun getMovieVideoCollection(idMovie: Long){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovieVideoCollection(id = idMovie)

        client.enqueue(object: retrofit2.Callback<MovieVideoCollection>{
            override fun onResponse(
                call: Call<MovieVideoCollection>,
                response: Response<MovieVideoCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _movieVideoResponse.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieVideoCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getMovieReviewCollection(idMovie: Long, page: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovieReviewCollection(id = idMovie, page = page)
        client.enqueue(object: retrofit2.Callback<MovieReviewCollection>{
            override fun onResponse(
                call: Call<MovieReviewCollection>,
                response: Response<MovieReviewCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _movieReviewCollection.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieReviewCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getMovieReviewCollectionMore(idMovie: Long, page: Int){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMovieReviewCollection(id = idMovie, page = page)
        client.enqueue(object: retrofit2.Callback<MovieReviewCollection>{
            override fun onResponse(
                call: Call<MovieReviewCollection>,
                response: Response<MovieReviewCollection>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _movieReviewCollection2.value = response.body()
                    _isFail.value = false
                    Log.d(TAG, "Success")
                }else{
                    _isFail.value = true
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieReviewCollection>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}