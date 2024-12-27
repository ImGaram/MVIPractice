package com.example.mvipractice.note_list.domain.usecase

import com.example.mvipractice.core.domain.model.NoteItem
import com.example.mvipractice.core.domain.repository.NoteRepository

class GetAllNotes(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(isOrderByTitle: Boolean): List<NoteItem> {
        return if (isOrderByTitle) {
            noteRepository.getAllNotes().sortedBy { it.title.lowercase() }
        } else {
            noteRepository.getAllNotes().sortedBy { it.dateAdded }
        }
    }
}