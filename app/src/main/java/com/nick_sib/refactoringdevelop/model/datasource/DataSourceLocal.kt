package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel
import io.reactivex.rxjava3.core.Observable


class DataSourceLocal(
    private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()
) : IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}