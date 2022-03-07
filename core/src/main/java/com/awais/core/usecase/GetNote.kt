package com.awais.core.usecase

import com.awais.core.repository.NoteRepository

class GetNote(private val noteRepository: NoteRepository) {
    suspend fun invoke(id: Long) = noteRepository.getNote(id)
}