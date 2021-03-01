package com.nick_sib.refactoringdevelop.view.main

import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.presenter.IInteractor

class MainInteractor<T, R>(
    private val repositoryRemote: IRepository<T, R>,
    private val repositoryLocal: IRepository<T, R>
) : IInteractor<T, R> {

    override suspend fun getData(word: R, fromRemoteSource: Boolean): T {
        val res = if (fromRemoteSource) {
            val data = repositoryRemote.getData(word)
            repositoryLocal.saveData(data)
            data
        } else {
            repositoryLocal.getData(word)
        }
        return res
    }

}