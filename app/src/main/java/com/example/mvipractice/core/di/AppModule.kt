package com.example.mvipractice.core.di

import android.app.Application
import androidx.room.Room
import com.example.mvipractice.core.data.local.NoteDb
import com.example.mvipractice.core.data.repository.NoteRepositoryImpl
import com.example.mvipractice.core.domain.repository.NoteRepository
import com.example.mvipractice.note_list.domain.usecase.DeleteNotes
import com.example.mvipractice.note_list.domain.usecase.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDb(application: Application): NoteDb {
        return Room.databaseBuilder(
            application,
            NoteDb::class.java,
            "note_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDb: NoteDb): NoteRepository {
        return NoteRepositoryImpl(noteDb)
    }

    @Provides
    @Singleton
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotes {
        return GetAllNotes(noteRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteNotesUseCase(noteRepository: NoteRepository): DeleteNotes {
        return DeleteNotes(noteRepository)
    }
}