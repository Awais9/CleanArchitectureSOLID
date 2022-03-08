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

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var useCases: UseCases

    init {
        DaggerViewModelComponent.builder().applicationModule(ApplicationModule(getApplication()))
            .build().inject(this)
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