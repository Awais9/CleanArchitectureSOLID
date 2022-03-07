package com.awais.cleanarchitecturesolid.framework

import com.awais.core.usecase.AddNote
import com.awais.core.usecase.GetAllNotes
import com.awais.core.usecase.GetNote
import com.awais.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote
)