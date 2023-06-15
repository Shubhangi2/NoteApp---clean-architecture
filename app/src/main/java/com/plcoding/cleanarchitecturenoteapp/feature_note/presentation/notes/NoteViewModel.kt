package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUsecases
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.reflect.Constructor
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteUsecases: NoteUsecases
): ViewModel() {
    private val _state = mutableStateOf(NotesState())
    val state : State<NotesState> = _state
    private var recentlyDeletedNote : Note ?= null
    private var getNotesJob: Job ?= null

    init {
        getNote(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvents){
        when(event){
            is NotesEvents.Order -> {
                if(state.value.noteOrder::class == event.noteOrder::class  &&
                        state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
            }
            is NotesEvents.DeleteNote ->{
                viewModelScope.launch {
                    noteUsecases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }

            }
            is NotesEvents.RestoreNote ->{
                viewModelScope.launch {
                    noteUsecases.addNote(recentlyDeletedNote?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvents.ToggleOrderSection ->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )

            }
        }

    }

    private fun getNote(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUsecases.getNotes(noteOrder)
            .onEach { notes ->
                _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)
            }
            .launchIn(viewModelScope)
    }
}