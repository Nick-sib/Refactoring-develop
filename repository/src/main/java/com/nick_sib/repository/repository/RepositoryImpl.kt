package com.nick_sib.repository.repository

import com.nick_sib.repository.IDataSource

class RepositoryImpl<T, R>(
    private val dataSource: IDataSource<T, R>
) : IRepository<T, R> {

    override suspend fun getData(request: R): T =
        dataSource.getData(request)

    override suspend fun setData(data: T): T =
        dataSource.saveData(data)

}