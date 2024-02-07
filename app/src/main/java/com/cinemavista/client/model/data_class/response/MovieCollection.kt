package com.cinemavista.client.model.data_class.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieCollection(
    var dates: MovieDates?= null,
    var page: Int = 0,
    var results: List<MovieInformation>?= null,
    var total_pages: Int = 0,
    var total_results: Int = 0
):Parcelable