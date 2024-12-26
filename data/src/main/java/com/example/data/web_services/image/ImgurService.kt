package com.example.data.web_services.image

import com.example.data.model.ImageResponseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST



interface ImgurService {
    @POST("image")
    fun uploadImage(@Body image: RequestBody): Call<ImageResponseModel>
}