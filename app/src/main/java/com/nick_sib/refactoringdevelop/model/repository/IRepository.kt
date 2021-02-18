package com.nick_sib.refactoringdevelop.model.repository

import io.reactivex.rxjava3.core.Observable


interface IRepository<T> {
    fun getData(word: String): Observable<T>
}