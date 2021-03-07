package com.nick_sib.refactoringdevelop.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nick_sib.refactoringdevelop.model.data.AppStateList
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.BaseInterceptor
import com.nick_sib.refactoringdevelop.model.datasource.RoomDataBaseDescriptionImpl
import com.nick_sib.refactoringdevelop.model.datasource.RoomDataBaseImpl
import com.nick_sib.refactoringdevelop.model.datasource.provider.ApiService
import com.nick_sib.refactoringdevelop.model.datasource.provider.RetrofitImpl
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.model.repository.RepositoryImpl
import com.nick_sib.refactoringdevelop.model.room.DataBase
import com.nick_sib.refactoringdevelop.view.activitys.description.DescriptionViewModel
import com.nick_sib.refactoringdevelop.view.activitys.history.HistoryViewModel
import com.nick_sib.refactoringdevelop.view.activitys.main.MainInteractor
import com.nick_sib.refactoringdevelop.view.activitys.main.MainViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"

val application = module {
    single { Room.databaseBuilder(get(), DataBase::class.java, "HistoryDB").build() }
    single { get<DataBase>().dataModelDao }
    single { get<DataBase>().meaningsDao }

    single<IRepository<List<DataModel>, String>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl(get())) }
    single<IRepository<List<DataModel>, String>>(named(NAME_LOCAL))  { RepositoryImpl(RoomDataBaseImpl(get())) }

    single<IRepository<DataModel, Long>> { RepositoryImpl(RoomDataBaseDescriptionImpl(get())) }


    factory<ApiService> {
        fun createOkHttpClient(interceptor: Interceptor): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        fun createRetrofit(interceptor: Interceptor): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL_LOCATIONS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(createOkHttpClient(interceptor))
                .build()

        return@factory createRetrofit(BaseInterceptor).create(ApiService::class.java)
    }
}

val mainScreen = module {
    factory { MainInteractor<AppStateList, String>(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { MainViewModel(get()) }
}

val descriprionScreen = module {
    factory { DescriptionViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get(named(NAME_LOCAL))) }
}