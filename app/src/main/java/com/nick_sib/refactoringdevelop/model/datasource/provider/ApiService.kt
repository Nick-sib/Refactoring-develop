package com.nick_sib.refactoringdevelop.model.datasource.provider

import com.nick_sib.refactoringdevelop.model.data.DataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("words/search")
    fun searchAsync(@Query("search") wordToSearch: String): Deferred<List<DataModel>>

    @GET("meanings")
    fun getDescriptionAsync(@Query("ids") id: Long): Deferred<List<DataModel>>

//    https://dictionary.skyeng.ru/api/public/v1/meanings?ids=100
}