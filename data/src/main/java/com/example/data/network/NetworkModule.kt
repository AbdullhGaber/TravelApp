package com.example.data.network



import com.example.data.uitls.Constants
import com.example.data.web_services.image.ImgurService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun provideFireStore() : FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    fun provideImgurInterceptor() : Interceptor{
        return Interceptor { chain ->

            val originalRequest = chain.request()

            val modifiedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Client-ID ${Constants.CLIENT_ID}")
                .build()

            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    fun provideImgurClient(imgurInterceptor: Interceptor) : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(imgurInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideImgurRetrofit(client : OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.IMGUR_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideImgurService(retrofit: Retrofit) : ImgurService{
        return retrofit.create(ImgurService::class.java)
    }

}