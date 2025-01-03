package com.example.mvipractice.core.data.remote.api

import com.example.mvipractice.core.data.remote.dto.ImageListDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("/api/")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("key") apiKey: String = API_KEY
    ): ImageListDTO?

    companion object {
        const val BASE_URL = "https://pixabay.com"
        const val API_KEY = "48012965-15dd7d7e7f2dc166c205a393c"
    }
}