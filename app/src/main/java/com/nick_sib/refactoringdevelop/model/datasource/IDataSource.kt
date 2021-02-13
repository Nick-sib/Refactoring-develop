package com.nick_sib.refactoringdevelop.model.datasource

import com.nick_sib.refactoringdevelop.model.data.DataModel
import io.reactivex.rxjava3.core.Observable


interface IDataSource<T> {
    fun getData(word: String): Observable<List<DataModel>>
}