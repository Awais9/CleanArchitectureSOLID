package com.awais.cleanarchitecturesolid.framework.di.components

import com.awais.cleanarchitecturesolid.framework.di.modules.ApplicationModule
import com.awais.cleanarchitecturesolid.framework.di.modules.RepositoryModule
import com.awais.cleanarchitecturesolid.framework.di.modules.UseCasesModule
import com.awais.cleanarchitecturesolid.framework.viewmodel.NoteListViewModel
import com.awais.cleanarchitecturesolid.framework.viewmodel.NoteViewModel
import dagger.Component

@Component(modules = [ApplicationModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {

    fun inject(noteViewModel: NoteViewModel)
    fun inject(noteListViewModel: NoteListViewModel)

}