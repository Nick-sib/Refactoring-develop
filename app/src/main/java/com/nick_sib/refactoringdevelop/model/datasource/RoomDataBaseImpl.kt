package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel
import io.reactivex.rxjava3.core.Observable

class RoomDataBaseImpl: IDataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> =
         Observable.fromSingle {
             it.onSuccess(emptyList())
         }
}