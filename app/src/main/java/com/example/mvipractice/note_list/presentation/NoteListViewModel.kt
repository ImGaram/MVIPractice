package com.example.mvipractice.note_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvipractice.core.domain.model.NoteItem
import com.example.mvipractice.note_list.domain.usecase.DeleteNotes
import com.example.mvipractice.note_list.domain.usecase.GetAllNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotes: GetAllNotes,
    private val deleteNotes: DeleteNotes
): ViewModel() {

    private val _noteListState = MutableStateFlow<List<NoteItem>>(emptyList())
    val noteListState = _noteListState.asStateFlow()

    private val _orderByTitleState = MutableStateFlow<Boolean>(false)
    val orderByTitleState = _orderByTitleState.asStateFlow()

    fun loadNotes() {
        viewModelScope.launch {
            _noteListState.update {
                getAllNotes.invoke(orderByTitleState.value)
            }
        }
    }

    fun deleteNote(noteItem: NoteItem) {
        viewModelScope.launch {
            deleteNotes.invoke(noteItem)
            loadNotes()
        }
    }

    fun changeOrder() {
        viewModelScope.launch {
            _orderByTitleState.update { !it }
            loadNotes()
        }
    }
}