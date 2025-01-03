package com.example.mvipractice.add_note.domain.usecase

import com.example.mvipractice.add_note.presentation.util.Resources
import com.example.mvipractice.core.domain.model.Images
import com.example.mvipractice.core.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchImages(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(query: String): Flow<Resources<Images>> {
        return flow {
            emit(Resources.Loading(true))

            if (query.isEmpty()) {
                emit(Resources.Error())
                emit(Resources.Loading(false))
                return@flow
            }

            val images = try {
                imagesRepository.searchImages(query)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resources.Error())
                emit(Resources.Loading(false))
                return@flow
            }

            images?.let {
                emit(Resources.Success(it))
                emit(Resources.Loading(false))
                return@flow
            }

            emit(Resources.Error())
            emit(Resources.Loading(false))
        }
    }
}