package com.awais.cleanarchitecturesolid.presentation.interfaces

import com.awais.core.data.Note

interface NoteClickListener {
    fun onClick(note: Note)
    fun onDelete(note: Note)
}