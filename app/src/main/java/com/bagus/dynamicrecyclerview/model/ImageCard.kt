package com.bagus.dynamicrecyclerview.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageCard(
    var image : String,
    var title : String,
    var description : String
) : Parcelable