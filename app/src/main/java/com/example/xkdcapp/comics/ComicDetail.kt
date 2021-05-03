package com.example.xkdcapp.comics

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicDetail(
    @SerializedName("safe_title") var title: String,
    @SerializedName("num") val id: String,
    @SerializedName("transcript") val description: String?,
    @SerializedName("alt") val message: String?,
    @SerializedName("img") val imageUrl: String
): Serializable