package com.awais.cleanarchitecturesolid.framework.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.awais.cleanarchitecturesolid.framework.UseCases
import com.awais.cleanarchitecturesolid.framework.di.components.DaggerViewModelComponent
import com.awais.cleanarchitecturesolid.framework.di.modules.ApplicationModule
import com.awais.core.data.Note
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder().applicationModule(ApplicationModule(getApplication()))
            .build().inject(this)
    }

    val loading by lazy { MutableLiveData<Boolean>() }
    val allNotes by lazy { MutableLiveData<List<Note>>() }

    fun getAllNotes() {
        loading.value = true
        viewModelScope.launch {
            val notes = useCases.getAllNotes.invoke()
            notes.forEach { it.wordCount = useCases.getWordCount.invoke(it) }
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

    fun deleteAllNotes() {
        loading.value = true
        viewModelScope.launch {
            useCases.deleteAllNote.invoke()
            getAllNotes()
        }
    }
}