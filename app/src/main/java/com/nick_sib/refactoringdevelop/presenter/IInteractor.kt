package com.nick_sib.refactoringdevelop.presenter

interface IInteractor<T, R> {
    suspend fun getData(request: R, fromRemoteSource: Boolean): T
    suspend fun saveData(data: T): Boolean
}