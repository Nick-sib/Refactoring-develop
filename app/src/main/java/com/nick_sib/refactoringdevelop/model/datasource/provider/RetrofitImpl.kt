package com.nick_sib.refactoringdevelop.model.datasource.provider

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.BaseInterceptor
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl: IDataSource<List<DataModel>, String> {

    override suspend fun getData(word: String): List<DataModel> =
        getService(BaseInterceptor).searchAsync(word).await()

    private fun getService(interceptor: Interceptor): ApiService =
        createRetrofit(interceptor).create(ApiService::class.java)

    private fun createRetrofit(interceptor: Interceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_LOCATIONS)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createOkHttpClient(interceptor))
            .build()

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    companion object {
        private const val BASE_URL_LOCATIONS = "https://dictionary.skyeng.ru/api/public/v1/"
    }

    override suspend fun saveData(data: List<DataModel>): List<DataModel> {
        TODO("Not yet implemented")
    }


}