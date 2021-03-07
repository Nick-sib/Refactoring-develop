package com.nick_sib.refactoringdevelop.model.datasource.provider

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource

class RetrofitDescriptionImpl(
    private val apiService: ApiService
): IDataSource<DataModel, Long> {

    override suspend fun getData(word: Long): DataModel =
        apiService.getDescriptionAsync(word).await()[0]

    override suspend fun saveData(data: DataModel): DataModel {
        TODO("Not yet implemented")
    }
}