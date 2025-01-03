package com.example.mvipractice.core.domain.repository

import com.example.mvipractice.core.domain.model.Images

interface ImagesRepository {

    suspend fun searchImages(query: String): Images?
}