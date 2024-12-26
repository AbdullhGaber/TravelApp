package com.example.domain.entity

data class ImageResponseEntity(
    val status : Int? = null,
    val success : Boolean = false,
    val data : ImageDataEntity? = null
)

data class ImageDataEntity(
    val link : String? = null
)
