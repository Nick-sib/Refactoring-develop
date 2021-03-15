package com.nick_sib.repository.api

import com.nick_sib.model.DataModel
import com.nick_sib.repository.IDataSource

class RetrofitImpl(
    private val apiService: ApiService
): IDataSource<List<DataModel>, String> {

    override suspend fun getData(word: String): List<DataModel> =
        apiService.searchAsync(word).await()

    override suspend fun saveData(data: List<DataModel>): List<DataModel> {
        TODO("Not yet implemented")
    }

}