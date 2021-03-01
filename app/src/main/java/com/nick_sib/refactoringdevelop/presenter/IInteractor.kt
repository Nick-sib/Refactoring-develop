package com.nick_sib.refactoringdevelop.presenter

interface IInteractor<T, R> {
    suspend fun getData(word: R, fromRemoteSource: Boolean): T
}