package com.awais.core.usecase

import com.awais.core.repository.NoteRepository

class DeleteAllNote(private val noteRepository: NoteRepository) {
    suspend fun invoke() = noteRepository.deleteAllNotes()
}