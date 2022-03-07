package com.awais.core.usecase

import com.awais.core.data.Note
import com.awais.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend fun invoke(note: Note) = noteRepository.removeNote(note)
}