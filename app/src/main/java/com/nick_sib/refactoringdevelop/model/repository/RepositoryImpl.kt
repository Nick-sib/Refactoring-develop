package com.nick_sib.refactoringdevelop.model.repository

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource


class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModel>>
) : IRepository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> =
        dataSource.getData(word)

}