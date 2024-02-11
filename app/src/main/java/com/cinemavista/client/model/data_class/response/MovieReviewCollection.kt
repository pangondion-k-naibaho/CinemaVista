package com.cinemavista.client.model.data_class.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReviewCollection(
    var id: Long?= 0L,
    var page: Int? = 0,
    var results: List<MovieReview>?= null,
    var total_pages: Int?= 0,
    var total_results: Int?= 0
): Parcelable