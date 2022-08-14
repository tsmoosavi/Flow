package com.example.flow.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItem(
    val author: String,
    val downloadUrl: String,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int,
) : Parcelable