package com.nick_sib.refactoringdevelop.model.repository

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource


class RepositoryImpl<T, R>(
    private val dataSource: IDataSource<T, R>
) : IRepository<T, R> {

    override suspend fun getData(word: R): T =
        dataSource.getData(word)

    override suspend fun saveData(data: T) {
        dataSource.saveData(data)
    }


}