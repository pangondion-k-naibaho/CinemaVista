package com.cinemavista.client.model.remote

import com.cinemavista.client.model.data_class.response.MovieCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    fun getListMovieNowPlaying(
        @Query("language") language: String?= null,
        @Query("page") page: Int,
        @Query("region") region: String?= null
    ): Call<MovieCollection>

    @GET("movie/popular")
    fun getListMoviePopular(
        @Query("language") language: String?= null,
        @Query("page") page: Int,
        @Query("region") region: String?= null
    ): Call<MovieCollection>

    @GET("movie/top_rated")
    fun getListMovieTopRated(
        @Query("language") language: String?= null,
        @Query("page") page: Int,
        @Query("region") region: String?= null
    ): Call<MovieCollection>

    @GET("movie/upcoming")
    fun getListMovieUpcoming(
        @Query("language") language: String?= null,
        @Query("page") page: Int,
        @Query("region") region: String?= null
    ): Call<MovieCollection>
}