package com.nick_sib.refactoringdevelop.di

import com.nick_sib.refactoringdevelop.App
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource
import com.nick_sib.refactoringdevelop.model.datasource.RoomDataBaseImpl
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.model.repository.RepositoryImpl
import com.nick_sib.refactoringdevelop.model.datasource.provider.RetrofitImpl
import com.nick_sib.refactoringdevelop.utils.network.INetworkStatus
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: IDataSource<List<DataModel>>): IRepository<List<DataModel>> =
        RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: IDataSource<List<DataModel>>): IRepository<List<DataModel>> =
        RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): IDataSource<List<DataModel>> =
        RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): IDataSource<List<DataModel>> = RoomDataBaseImpl()
}
