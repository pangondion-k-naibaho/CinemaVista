package com.cinemavista.client.model.data_class.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AuthorDetail(
    var name: String?= "",
    var username: String?= "",
    var avatar_path: String?= "",
    var rating: String? = ""
): Parcelable