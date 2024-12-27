package com.example.mvipractice.note_list.domain.usecase

import com.example.mvipractice.core.domain.model.NoteItem
import com.example.mvipractice.core.domain.repository.NoteRepository

class DeleteNotes(
    private val noteRepository: NoteRepository
) {

    suspend operator fun invoke(noteItem: NoteItem) {
        noteRepository.deleteNote(noteItem)
    }
}