package com.cinemavista.client.model

import android.graphics.drawable.AnimatedImageDrawable

class Constants {

    interface URL_CONSTANTS{
        companion object{
            const val TMDBAPI_URL = "https://api.themoviedb.org/3/"
        }
    }

    interface KEY{
        companion object{
            const val TMDBAPI_KEY = "f0e9e0e9f224c4feb567ad64ae5f0d04"
            const val TMDBAPI_BEARER = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmMGU5ZTBlOWYyMjRjNGZlYjU2N2FkNjRhZTVmMGQwNCIsInN1YiI6IjYxNTM3YThkZThhM2UxMDA4YWU0NTgyZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.75AtCGmeKXpaI3QPAlXwlrbWuahAhpr2fu-M_RkIf3Y"
        }
    }

    interface URL{
        companion object{
            const val TMDBIMAGE_URL = "https://image.tmdb.org/t/p/w500"
            const val YOUTUBE_URL = "https://youtube.com"
            const val TMDBAVATAR_URL = "https://www.gravatar.com/avatar"
        }
    }

    interface SORTING_CONSTANTS{
        companion object{
            const val ORIGINAL_TITLE_ASC = "original_title.asc"
            const val ORIGINAL_TITLE_DESC = "original_title.desc"
            const val POPULARITY_ASC = "popularity.asc"
            const val POPULARITY_DESC = "popularity.desc"
            const val REVENUE_ASC = "revenue.asc"
            const val REVENUE_DESC = "revenue.desc"
            const val PRIMARY_RELEASE_DATE_ASC = "primary_release_date.asc"
            const val PRIMARY_RELEASE_DATE_DESC = "primary_release_date.desc"
            const val TITLE_ASC = "title.asc"
            const val TITLE_DESC = "title.desc"
            const val VOTE_AVERAGE_ASC = "vote_average.asc"
            const val VOTE_AVERAGE_DESC = "vote_average.desc"
            const val VOTE_COUNT_ASC = "vote_count.asc"
            const val VOTE_COUNT_DESC = "vote_count.desc"
        }
    }

    interface GENRE_CONSTANTS{
        companion object{
            const val ACTION = 28
            const val ADVENTURE = 12
            const val ANIMATION = 16
            const val COMEDY = 35
            const val CRIME = 80
            const val DOCUMENTARY = 99
            const val DRAMA = 18
            const val FAMILY = 10751
            const val FANTASY = 14
            const val HISTORY = 36
            const val HORROR = 36
            const val MUSIC = 10402
            const val MYSTERY = 9648
            const val ROMANCE = 10749
            const val SCIENCE_FICTION = 878
            const val TV_MOVIE = 10770
            const val THRILLER = 53
            const val WAR = 10752
            const val WESTERN = 37
        }
    }

    interface LANGUAGE{
        companion object{
            const val EN_US = "en-US"
        }
    }
}