package com.example.mvipractice.add_note.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvipractice.add_note.presentation.util.Resources
import com.example.mvipractice.core.data.repository.FakeImagesRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchImagesTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeImagesRepository: FakeImagesRepository
    private lateinit var searchImages: SearchImages

    @Before
    fun setUp() {
        fakeImagesRepository = FakeImagesRepository()
        searchImages = SearchImages(fakeImagesRepository)
    }

    @Test
    fun `search images with empty query, returns error`() = runTest {
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resources.Error -> {
                    assertThat(result.data?.images != null).isTrue()
                }
                is Resources.Success -> Unit
                is Resources.Loading -> Unit
            }
        }
    }

    @Test
    fun `search images a valid query but with network error, returns error`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)
        searchImages.invoke("").collect { result ->
            when (result) {
                is Resources.Error -> {
                    assertThat(result.data?.images == null).isTrue()
                }
                is Resources.Success -> Unit
                is Resources.Loading -> Unit
            }
        }
    }

    @Test
    fun `search images a valid query, returns success`() = runTest {
        searchImages.invoke("query").collect { result ->
            when (result) {
                is Resources.Success -> {
                    assertThat(result.data?.images != null).isTrue()
                }
                is Resources.Error -> Unit
                is Resources.Loading -> Unit
            }
        }
    }

    @Test
    fun `search images a valid query, list is not empty`() = runTest {
        searchImages.invoke("query").collect { result ->
            when (result) {
                is Resources.Success -> {
                    assertThat(result.data?.images?.size!! > 0).isTrue()
                }
                is Resources.Error -> Unit
                is Resources.Loading -> Unit
            }
        }
    }
}