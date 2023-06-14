package com.ntes_app.notesScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ntes_app.model.Note
import com.ntes_app.repositories.NoteRepository
import com.ntes_app.repositories.SharedPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesScreenViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val sharedPreferenceRepository: SharedPreferenceRepository
) : ViewModel() {

    val notesList = MutableLiveData<ArrayList<Note>>()

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesList.postValue(
                sharedPreferenceRepository.getCurrentUserEmail()?.let {
                    noteRepository.getNotesByEmail(
                        it
                    )
                }
            )
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
            getAllNotes()
        }
    }
}