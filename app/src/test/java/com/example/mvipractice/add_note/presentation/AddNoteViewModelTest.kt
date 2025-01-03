package com.example.mvipractice.add_note.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvipractice.add_note.domain.usecase.UpsertNote
import com.example.mvipractice.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var addNoteViewModel: AddNoteViewModel
    private lateinit var fakeNoteRepositoryImpl: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepositoryImpl = FakeNoteRepository()
        val upsertNote = UpsertNote(fakeNoteRepositoryImpl)
        addNoteViewModel = AddNoteViewModel(upsertNote)
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
}