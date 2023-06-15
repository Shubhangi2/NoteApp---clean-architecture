package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.noteDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepositoryImp
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.*
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
        fun provideNoteDatabase(app: Application): noteDatabase{
                return Room.databaseBuilder(
                        app,
                        noteDatabase::class.java,
                        noteDatabase.DATABASE_NAME

                ).build()
        }

        @Provides
        @Singleton
        fun provideNoteRepository(db: noteDatabase): NoteRepository{
                return NoteRepositoryImp(db.noteDao)
        }

        @Provides
        @Singleton
        fun provideNoteUseCases(repository: NoteRepository): NoteUsecases{
                return NoteUsecases(
                        getNotes = GetNotes(repository),
                        deleteNote = DeleteNote(repository),
                        addNote = AddNote(repository),
                        getNote = GetNote(repository)
                )
        }
}