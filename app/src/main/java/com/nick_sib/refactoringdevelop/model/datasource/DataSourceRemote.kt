package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.datasource.provider.RetrofitImpl
import io.reactivex.rxjava3.core.Observable


class DataSourceRemote(
    private val remoteProvider: RetrofitImpl = RetrofitImpl()
) : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)

}