package com.nick_sib.refactoringdevelop.presenter

interface IInteractor<T> {
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}