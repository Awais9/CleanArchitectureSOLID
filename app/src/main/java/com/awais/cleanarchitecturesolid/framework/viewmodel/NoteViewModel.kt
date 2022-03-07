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
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository by lazy { NoteRepository(RoomNoteDataSource(application)) }

    private val useCases by lazy {
        UseCases(
            AddNote(repository),
            GetAllNotes(repository),
            GetNote(repository),
            RemoveNote(repository)
        )
    }

    val saved by lazy { MutableLiveData<Boolean>() }
    val note by lazy { MutableLiveData<Note?>() }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            useCases.addNote.invoke(note)
            saved.postValue(true)
        }
    }

    fun getNote(id: Long) {
        viewModelScope.launch {
            val dbNote = useCases.getNote.invoke(id)
            note.value = dbNote
        }
    }

}