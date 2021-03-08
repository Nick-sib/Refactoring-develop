package com.nick_sib.refactoringdevelop.di

import androidx.room.Room
import com.nick_sib.model.AppStateList
import com.nick_sib.model.DataModel
import com.nick_sib.refactoringdevelop.view.activitys.history.HistoryViewModel
import com.nick_sib.core.MainInteractor
import com.nick_sib.refactoringdevelop.view.activitys.main.MainViewModel
import com.nick_sib.repository.datasource.RoomDataBaseDescriptionImpl
import com.nick_sib.repository.datasource.RoomDataBaseImpl
import com.nick_sib.repository.api.RetrofitImpl
import com.nick_sib.repository.repository.IRepository
import com.nick_sib.repository.repository.RepositoryImpl
import com.nick_sib.repository.room.DataBase
import com.nick_sib.repository.api.ApiService
import com.nick_sib.repository.api.BaseInterceptor
import com.nick_sib.repository.api.createRetrofit
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), DataBase::class.java, "HistoryDB").build() }
    single { get<DataBase>().dataModelDao }
    single { get<DataBase>().meaningsDao }

    single<IRepository<List<DataModel>, String>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl(get())) }
    single<IRepository<List<DataModel>, String>>(named(NAME_LOCAL))  { RepositoryImpl(RoomDataBaseImpl(get())) }

    single<IRepository<DataModel, Long>> { RepositoryImpl(RoomDataBaseDescriptionImpl(get())) }

    factory { createRetrofit(BaseInterceptor).create(ApiService::class.java) }
}

val mainScreen = module {
    factory { MainInteractor<AppStateList, String>(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}

val descriprionScreen = module {
    factory { com.nick_sib.descriptionscreen.DescriptionViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get(named(NAME_LOCAL))) }
}