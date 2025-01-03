package com.example.mvipractice.core.data.repository

import com.example.mvipractice.core.domain.model.Images
import com.example.mvipractice.core.domain.repository.ImagesRepository

class FakeAndroidImagesRepository: ImagesRepository {

    private var shouldReturnError = false
    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun searchImages(query: String): Images? {
        return if (shouldReturnError) {
            null
        } else {
            Images(images = listOf("image1", "image2", "image3", "image4"))
        }
    }
}