package com.nick_sib.repository.api

import com.nick_sib.model.DataModel
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