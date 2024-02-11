package com.cinemavista.client.model.data_class.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieReview(
    var author: String? = "",
    var author_details: AuthorDetail?= null,
    var content: String?= "",
    var created_at: String?= "",
    var id: String? = "",
    var updated_at: String?= "",
    var url: String?= ""
): Parcelable