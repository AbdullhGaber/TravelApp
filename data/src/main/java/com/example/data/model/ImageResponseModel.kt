package com.example.data.model

import com.google.gson.annotations.SerializedName

data class ImageResponseModel(
    @SerializedName("status")
    val status : Int? = null,

    @SerializedName("success")
    val success : Boolean = false,

    @SerializedName("data")
    val data : ImageDataModel? = null
)

data class ImageDataModel(
    @SerializedName("link")
    val link : String? = null
)
