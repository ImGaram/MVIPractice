package com.example.mvipractice.add_note.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvipractice.add_note.domain.usecase.SearchImages
import com.example.mvipractice.add_note.domain.usecase.UpsertNote
import com.example.mvipractice.add_note.presentation.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val upsertNote: UpsertNote,
    private val searchImages: SearchImages
): ViewModel() {

    private val _addNoteState = MutableStateFlow(AddNoteState())
    val addNoteState = _addNoteState.asStateFlow()

    private val _noteSavedChannel = Channel<Boolean>()
    val noteSavedFlow = _noteSavedChannel.receiveAsFlow()

    fun onAction(action: AddNoteActions) {
        when (action) {
            is AddNoteActions.UpdateTitle -> {
                _addNoteState.update {
                    it.copy(title = action.newTitle)
                }
            }

            is AddNoteActions.UpdateDescription -> {
                _addNoteState.update {
                    it.copy(description = action.newDescription)
                }
            }

            is AddNoteActions.PickImage -> {
                _addNoteState.update {
                    it.copy(imageUrl = action.imageUrl)
                }
            }

            is AddNoteActions.UpdateSearchImageQuery -> {
                _addNoteState.update {
                    it.copy(searchImagesQuery = action.newQuery)
                }
                searchImages(action.newQuery)
            }

            AddNoteActions.UpdateImagesDialogVisibility -> {
                _addNoteState.update {
                    it.copy(isImagesDialogShowing = !it.isImagesDialogShowing)
                }
            }

            AddNoteActions.SaveNote -> {
                viewModelScope.launch {
                    val isSaved = upsertNote(
                        title = addNoteState.value.title,
                        description = addNoteState.value.description,
                        imageUrl = addNoteState.value.imageUrl
                    )

                    _noteSavedChannel.send(isSaved)
                }
            }
        }
    }

    suspend fun upsertNote(
        title: String,
        description: String,
        imageUrl: String
    ): Boolean {
        return upsertNote.invoke(
            title = title,
            description = description,
            imageUrl = imageUrl
        )
    }

    private var searchJob: Job? = null

    fun searchImages(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)

            searchImages.invoke(query)
                .collect { result ->
                    when (result) {
                        is Resources.Error -> {
                            _addNoteState.update {
                                it.copy(imageList = emptyList())
                            }
                        }

                        is Resources.Loading -> {
                            _addNoteState.update {
                                it.copy(isLoadingImages = result.isLoading)
                            }
                        }

                        is Resources.Success -> {
                            _addNoteState.update {
                                it.copy(
                                    imageList = result.data?.images ?: emptyList()
                                )
                            }
                        }
                    }
                }
        }
    }
}