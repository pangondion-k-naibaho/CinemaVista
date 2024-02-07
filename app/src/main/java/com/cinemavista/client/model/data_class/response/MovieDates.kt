package com.cinemavista.client.model.data_class.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDates(
    var maximum: String? = "",
    var minimum: String? = ""
): Parcelable