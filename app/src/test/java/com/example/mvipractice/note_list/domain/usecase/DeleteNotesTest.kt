package com.example.mvipractice.note_list.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvipractice.core.data.repository.FakeNoteRepository
import com.example.mvipractice.core.domain.model.NoteItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DeleteNotesTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNoteRepository: FakeNoteRepository
    private lateinit var deleteNotes: DeleteNotes

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        deleteNotes = DeleteNotes(fakeNoteRepository)

    }

    @Test
    fun `delete note from list, empty list`() = runTest {
        fakeNoteRepository.shouldHaveFilledList(false)

        val note = NoteItem(
            "c title 2",
            "desc 2",
            "url2",
            2
        )

        deleteNotes.invoke(note)

        assertThat(fakeNoteRepository.getAllNotes().contains(note)).isFalse()
    }
}