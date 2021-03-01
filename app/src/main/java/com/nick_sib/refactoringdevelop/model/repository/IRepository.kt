package com.nick_sib.refactoringdevelop.model.repository

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single


interface IRepository<T> {
    fun getData(word: String): Observable<T>
}