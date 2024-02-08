package com.cinemavista.client.model.remote

import com.cinemavista.client.model.Constants
import com.cinemavista.client.model.data_class.response.MovieCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.Date

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

    @GET("discover/movie")
    fun getDiscoveredMovie(
        @Query("certification") certification: String?= null,
        @Query("certification.gte") certificationGte: String?=null,
        @Query("certification.lte") certificationLte: String?= null,
        @Query("certification_country") certificationCountry: String?= null,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("primary_release_date.gte") primaryReleaseDateGte: Date?= null,
        @Query("primary_release_date.lte") primaryReleaseDateLte: Date?= null,
        @Query("region") region: String?= null,
        @Query("release_date.gte") releaseDateGte: Date?= null,
        @Query("release_date.lte") releaseDateLte: Date?= null,
        @Query("sort_by") sortBy: String = Constants.SORTING_CONSTANTS.POPULARITY_DESC,
        @Query("vote_average.gte") voteAverageGte: Float?= null,
        @Query("vote_average.lte") voteAverageLte: Float?= null,
        @Query("vote_count.gte") voteCountGte: Float?= null,
        @Query("vote_count.lte") voteCountLte: Float?= null,
        @Query("watch_region") watchRegion: String?= null,
        @Query("with_cast") withCast: String?= null,
        @Query("with_companies") withCompanies: String?= null,
        @Query("with_crew") withCrew: String?= null,
        @Query("with_genres") withGenres: String?= null,
        @Query("with_keywords") withKeywords: String?= null,
        @Query("with_origin_country") withOriginCountry: String?= null,
        @Query("with_original_language") withOriginalLanguage: String?= null,
        @Query("with_people") withPeople: String?= null,
        @Query("with_release_type") withReleaseType: Int?= null,
        @Query("with_runtime.gte") withRuntimeGte: Int?= null,
        @Query("with_runtime.lte") withRuntimeLte: Int?= null,
        @Query("with_watch_monetization_types") withWatchMoetizationTypes: String?= null,
        @Query("with_watch_providers") withWatchProviders: String?= null,
        @Query("without_companies") withoutCompanies: String?= null,
        @Query("without_genres") withoutGenres: String?= null,
        @Query("without_keywords") withoutKeywords: String?= null,
        @Query("without_watch_providers") withoutWatchProviders: String?= null,
        @Query("year") year: Int?= null
    ): Call<MovieCollection>
}