package com.nick_sib.refactoringdevelop.view.activitys.main

interface IInteractor<T, R> {
    suspend fun getData(request: R, fromRemoteSource: Boolean): T
    suspend fun saveData(data: T): Boolean
}