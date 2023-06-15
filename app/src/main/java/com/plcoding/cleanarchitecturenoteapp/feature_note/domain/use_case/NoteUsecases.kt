package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class NoteUsecases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote
)