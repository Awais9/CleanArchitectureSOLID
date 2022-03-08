package com.awais.cleanarchitecturesolid.framework.di.modules

import android.app.Application
import com.awais.cleanarchitecturesolid.framework.RoomNoteDataSource
import com.awais.core.repository.NoteRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesRepository(application: Application) =
        NoteRepository(RoomNoteDataSource(application))
}