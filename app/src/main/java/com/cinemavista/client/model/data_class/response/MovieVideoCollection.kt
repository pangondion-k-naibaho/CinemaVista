package com.cinemavista.client.model.data_class.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieVideoCollection(
    var id: Long = 0L,
    var results: List<MovieVideoInformation>?= null
): Parcelable
