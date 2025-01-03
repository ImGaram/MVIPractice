package com.example.mvipractice.core.data.repository

import com.example.mvipractice.core.data.mapper.toImages
import com.example.mvipractice.core.data.remote.api.ImagesApi
import com.example.mvipractice.core.domain.model.Images
import com.example.mvipractice.core.domain.repository.ImagesRepository
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val imagesApi: ImagesApi
): ImagesRepository {

    override suspend fun searchImages(query: String): Images? {
        return imagesApi.searchImages(query)?.toImages()
    }
}