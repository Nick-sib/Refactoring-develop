package com.nick_sib.refactoringdevelop.di

import androidx.room.Room
import com.nick_sib.refactoringdevelop.model.data.AppStateList
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.RoomDataBaseImpl
import com.nick_sib.refactoringdevelop.model.datasource.provider.RetrofitImpl
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.model.repository.RepositoryImpl
import com.nick_sib.refactoringdevelop.model.room.DataBase
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import com.nick_sib.refactoringdevelop.view.main.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {
    single { Room.databaseBuilder(get(), DataBase::class.java, "HistoryDB").build() }
    single { get<DataBase>().dataModelDao }
    single { get<DataBase>().meaningsDao }

    single<IRepository<List<DataModel>, String>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl()) }
    single<IRepository<List<DataModel>, String>>(named(NAME_LOCAL))  { RepositoryImpl(RoomDataBaseImpl(get())) }
}

val mainScreen = module {
    factory { MainInteractor<AppStateList, String>(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}

//val favorityScreen = module {
//    factory { FavorityViewModel(get()) }
//    factory { FavorityInteractor(get(), get()) }
//}