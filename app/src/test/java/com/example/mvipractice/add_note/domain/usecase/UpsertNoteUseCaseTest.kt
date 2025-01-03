package com.example.mvipractice.add_note.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvipractice.MainCoroutineRule
import com.example.mvipractice.core.data.repository.FakeNoteRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UpsertNoteUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var fakeNoteRepositoryImpl: FakeNoteRepository
    private lateinit var upsertNote: UpsertNote

    @Before
    fun setUp() {
        fakeNoteRepositoryImpl = FakeNoteRepository()
        upsertNote = UpsertNote(fakeNoteRepositoryImpl)
    }

    @Test
    fun `upsert note with empty title, return false`() = runTest {
        val isInserted = upsertNote.invoke(
            title = "",
            description = "description",
            imageUrl = "image"
        )

        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert note with empty description, return false`() = runTest {
        val isInserted = upsertNote.invoke(
            title = "title",
            description = "",
            imageUrl = "image"
        )

        assertThat(isInserted).isFalse()
    }

    @Test
    fun `upsert a valid, return true`() = runTest {
        val isInserted = upsertNote.invoke(
            title = "title",
            description = "description",
            imageUrl = "image"
        )

        assertThat(isInserted).isTrue()
    }
}