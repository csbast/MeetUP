package com.example.meetup.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geo(
    var lat: Double?,
    var lng: Double?
): Parcelable
