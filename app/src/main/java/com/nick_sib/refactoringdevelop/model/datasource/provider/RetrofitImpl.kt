package com.nick_sib.refactoringdevelop.model.datasource.provider

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource

class RetrofitImpl(
    private val apiService: ApiService
): IDataSource<List<DataModel>, String> {

    override suspend fun getData(word: String): List<DataModel> =
        apiService.searchAsync(word).await()

    override suspend fun saveData(data: List<DataModel>): List<DataModel> {
        TODO("Not yet implemented")
    }

}