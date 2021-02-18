package com.nick_sib.refactoringdevelop.di

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: IRepository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: IRepository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
