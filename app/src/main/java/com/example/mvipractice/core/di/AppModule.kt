package com.example.mvipractice.core.di

import android.app.Application
import androidx.room.Room
import com.example.mvipractice.add_note.domain.usecase.SearchImages
import com.example.mvipractice.add_note.domain.usecase.UpsertNote
import com.example.mvipractice.core.data.local.NoteDb
import com.example.mvipractice.core.data.remote.api.ImagesApi
import com.example.mvipractice.core.data.repository.ImagesRepositoryImpl
import com.example.mvipractice.core.data.repository.NoteRepositoryImpl
import com.example.mvipractice.core.domain.repository.ImagesRepository
import com.example.mvipractice.core.domain.repository.NoteRepository
import com.example.mvipractice.note_list.domain.usecase.DeleteNotes
import com.example.mvipractice.note_list.domain.usecase.GetAllNotes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Provides
    @Singleton
    fun provideUpsertNoteUseCase(noteRepository: NoteRepository): UpsertNote {
        return UpsertNote(noteRepository)
    }

    @Provides
    @Singleton
    fun provideImagesApi(): ImagesApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ImagesApi.BASE_URL)
            .build()
            .create(ImagesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImagesRepository(imagesApi: ImagesApi): ImagesRepository {
        return ImagesRepositoryImpl(imagesApi)
    }

    @Provides
    @Singleton
    fun provideSearchImagesUseCase(imagesRepository: ImagesRepository): SearchImages {
        return SearchImages(imagesRepository)
    }
}