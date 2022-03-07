package com.awais.cleanarchitecturesolid.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.awais.cleanarchitecturesolid.framework.RoomNoteDataSource
import com.awais.cleanarchitecturesolid.framework.UseCases
import com.awais.core.data.Note
import com.awais.core.repository.NoteRepository
import com.awais.core.usecase.AddNote
import com.awais.core.usecase.GetAllNotes
import com.awais.core.usecase.GetNote
import com.awais.core.usecase.RemoveNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy { NoteRepository(RoomNoteDataSource(application)) }
    private val useCases by lazy {
        UseCases(
            AddNote(repository),
            GetAllNotes(repository), GetNote(repository), RemoveNote(repository)
        )
    }

    val loading by lazy { MutableLiveData<Boolean>() }
    val allNotes by lazy { MutableLiveData<List<Note>>() }

    fun getAllNotes() {
        loading.value = true
        viewModelScope.launch {
            val notes = useCases.getAllNotes.invoke()
            allNotes.value = notes
            loading.value = false
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            useCases.removeNote.invoke(note)
            getAllNotes()
        }
    }
}