package com.nick_sib.refactoringdevelop.model.repository

import com.nick_sib.refactoringdevelop.model.datasource.IDataSource

class RepositoryImpl<T, R>(
    private val dataSource: IDataSource<T, R>
) : IRepository<T, R> {

    override suspend fun getData(request: R): T =
        dataSource.getData(request)

    override suspend fun setData(data: T): T =
        dataSource.saveData(data)

}