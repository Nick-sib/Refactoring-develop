package com.nick_sib.refactoringdevelop.model.datasource.provider

import com.nick_sib.refactoringdevelop.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("words/search")
    fun search(@Query("search") wordToSearch: String): Call<List<DataModel>>
}