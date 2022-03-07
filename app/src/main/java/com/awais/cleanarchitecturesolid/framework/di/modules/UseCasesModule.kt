package com.awais.cleanarchitecturesolid.framework.di.modules

import com.awais.cleanarchitecturesolid.framework.UseCases
import com.awais.core.repository.NoteRepository
import com.awais.core.usecase.AddNote
import com.awais.core.usecase.GetAllNotes
import com.awais.core.usecase.GetNote
import com.awais.core.usecase.RemoveNote
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )
}