package com.nick_sib.refactoringdevelop.di

import androidx.room.Room
import com.nick_sib.model.AppStateList
import com.nick_sib.model.DataModel
import com.nick_sib.core.MainInteractor
import com.nick_sib.refactoringdevelop.view.activitys.MainActivity
import com.nick_sib.refactoringdevelop.view.activitys.MainViewModel
import com.nick_sib.repository.datasource.RoomDataBaseDescriptionImpl
import com.nick_sib.repository.datasource.RoomDataBaseImpl
import com.nick_sib.repository.api.RetrofitImpl
import com.nick_sib.repository.repository.IRepository
import com.nick_sib.repository.repository.RepositoryImpl
import com.nick_sib.repository.room.DataBase
import com.nick_sib.repository.api.ApiService
import com.nick_sib.repository.api.BaseInterceptor
import com.nick_sib.repository.api.createRetrofit
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, mainScreen, descriprionScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), DataBase::class.java, "HistoryDB").build() }
    single { get<DataBase>().dataModelDao }
    single { get<DataBase>().meaningsDao }

    single<IRepository<List<DataModel>, String>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl(get())) }
    single<IRepository<List<DataModel>, String>>(named(NAME_LOCAL))  { RepositoryImpl(RoomDataBaseImpl(get())) }

    single<IRepository<DataModel, Long>> { RepositoryImpl(RoomDataBaseDescriptionImpl(get())) }

    factory { createRetrofit(BaseInterceptor).create(ApiService::class.java) }
}

//val mainScreen = module {
//    factory { MainInteractor<AppStateList, String>(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
//    factory { MainViewModel(get()) }
//}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped { MainInteractor<AppStateList, String>(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
        viewModel { MainViewModel(get()) }
    }
}

val descriprionScreen = module {
    factory { com.nick_sib.descriptionscreen.DescriptionViewModel(get()) }
}
