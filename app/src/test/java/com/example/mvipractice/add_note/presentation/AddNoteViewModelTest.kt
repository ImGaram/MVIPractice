package com.example.mvipractice.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvipractice.MainCoroutineRule
import com.example.mvipractice.add_note.domain.usecase.SearchImages
import com.example.mvipractice.add_note.domain.usecase.UpsertNote
import com.example.mvipractice.core.data.repository.FakeImagesRepository
import com.example.mvipractice.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var fakeNoteRepositoryImpl: FakeNoteRepository
    private lateinit var fakeImagesRepository: FakeImagesRepository

    @Before
    fun setUp() {
        fakeNoteRepositoryImpl = FakeNoteRepository()
        fakeImagesRepository = FakeImagesRepository()
        val upsertNote = UpsertNote(fakeNoteRepositoryImpl)
        val searchImages = SearchImages(fakeImagesRepository)
        addNoteViewModel = AddNoteViewModel(upsertNote, searchImages)
    }

    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val isInserted = addNoteViewModel.upsertNote(
            title = "",
            description = "description",
            imageUrl = "image"
        )

        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val isInserted = addNoteViewModel.upsertNote(
            title = "title",
            description = "",
            imageUrl = "image"
        )

        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert a valid, return true`() = runTest {
        val isInserted = addNoteViewModel.upsertNote(
            title = "title",
            description = "description",
            imageUrl = "image"
        )

        assertThat(isInserted).isTrue()
    }

    @Test
    fun `search image with empty query, image list is empty`() = runTest {
        addNoteViewModel.searchImages("")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isTrue()
    }

    @Test
    fun `search image with a valid query but with error, image list is empty`() = runTest {
        fakeImagesRepository.setShouldReturnError(true)

        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(addNoteViewModel.addNoteState.value.imageList.isEmpty()).isTrue()
    }

    @Test
    fun `search image with a valid query, image list is not empty`() = runTest {
        addNoteViewModel.searchImages("query")
        mainCoroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertThat(addNoteViewModel.addNoteState.value.imageList.isNotEmpty()).isTrue()
    }
}