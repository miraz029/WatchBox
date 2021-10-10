package com.miraz029.watchbox.api

import androidx.lifecycle.LiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.miraz029.watchbox.BuildConfig
import com.miraz029.watchbox.data.model.MovieDetails
import com.miraz029.watchbox.data.model.MovieResponse
import com.miraz029.watchbox.utilities.API_KEY
import com.miraz029.watchbox.utilities.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET(".")
    fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = API_KEY
    ): LiveData<ApiResponse<MovieResponse>>

    @GET(".")
    fun getMovieDetails(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = API_KEY
    ): LiveData<ApiResponse<MovieDetails>>

    companion object {

        fun create(): ApiService {

            val logger = HttpLoggingInterceptor().apply {
                level = if (!BuildConfig.IS_PRELIVE) {
                    Level.BODY
                } else {
                    Level.NONE
                }
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .build()

            val factory = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(factory))
                .addCallAdapterFactory(LiveDataAdapterFactory())
                .build()
                .create(ApiService::class.java)
        }
    }
}
