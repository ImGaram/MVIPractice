package com.example.mvipractice.core.domain.repository

import com.example.mvipractice.core.domain.model.NoteItem

interface NoteRepository {

    suspend fun upsertNote(noteItem: NoteItem)

    suspend fun deleteNote(noteItem: NoteItem)

    suspend fun getAllNotes(): List<NoteItem>
}