package com.cinemavista.client.model.data_class.request

import com.cinemavista.client.model.Constants.SORTING_CONSTANTS.Companion.POPULARITY_DESC
import retrofit2.http.Query
import java.util.Date

data class DiscoverMovie(
    @Query("certification") var certification: String?= null,
    @Query("certification.gte") var certificationGte: String?=null,
    @Query("certification.lte") var certificationLte: String?= null,
    @Query("certification_country") var certificationCountry: String?= null,
    @Query("include_adult") var includeAdult: Boolean = false,
    @Query("include_video") var includeVideo: Boolean = false,
    @Query("language") var language: String = "en-US",
    @Query("page") var page: Int,
    @Query("primary_release_year") var primaryReleaseYear: Int? = null,
    @Query("primary_release_date.gte") var primaryReleaseDateGte: Date?= null,
    @Query("primary_release_date.lte") var primaryReleaseDateLte: Date?= null,
    @Query("region") var region: String?= null,
    @Query("release_date.gte") var releaseDateGte: Date?= null,
    @Query("release_date.lte") var releaseDateLte: Date?= null,
    @Query("sort_by") var sortBy: String = POPULARITY_DESC,
    @Query("vote_average.gte") var voteAverageGte: Float?= null,
    @Query("vote_average.lte") var voteAverageLte: Float?= null,
    @Query("vote_count.gte") var voteCountGte: Float?= null,
    @Query("vote_count.lte") var voteCountLte: Float?= null,
    @Query("watch_region") var watchRegion: String?= null,
    @Query("with_cast") var withCast: String?= null,
    @Query("with_companies") var withCompanies: String?= null,
    @Query("with_crew") var withCrew: String?= null,
    @Query("with_genres") var withGenres: String?= null,
    @Query("with_keywords") var withKeywords: String?= null,
    @Query("with_origin_country") var withOriginCountry: String?= null,
    @Query("with_original_language") var withOriginalLanguage: String?= null,
    @Query("with_people") var withPeople: String?= null,
    @Query("with_release_type") var withReleaseType: Int?= null,
    @Query("with_runtime.gte") var withRuntimeGte: Int?= null,
    @Query("with_runtime.lte") var withRuntimeLte: Int?= null,
    @Query("with_watch_monetization_types") var withWatchMoetizationTypes: String?= null,
    @Query("with_watch_providers") var withWatchProviders: String?= null,
    @Query("without_companies") var withoutCompanies: String?= null,
    @Query("without_genres") var withoutGenres: String?= null,
    @Query("without_keywords") var withoutKeywords: String?= null,
    @Query("without_watch_providers") var withoutWatchProviders: String?= null,
    @Query("year") var year: Int?= null
)