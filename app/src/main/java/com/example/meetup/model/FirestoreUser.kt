package com.example.meetup.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FirestoreUser(
    val first: String = "",
    val last: String = "",
    val email: String = "",
    val phone: String = "",
    var cpf: String = "",
    var id: String = "",
    var birthday: String? = null,
    var birthtime: String? = null,
    var imageBitmap: Bitmap? = null,
    var address: Address? = null
): Parcelable
