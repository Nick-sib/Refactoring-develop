package com.nick_sib.refactoringdevelop.model.repository

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.IDataSource
import io.reactivex.rxjava3.core.Observable


class RepositoryImpl(
    private val dataSource: IDataSource<List<DataModel>>
) : IRepository<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
        dataSource.getData(word)
}