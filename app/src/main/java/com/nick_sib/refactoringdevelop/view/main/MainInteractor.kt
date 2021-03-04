package com.nick_sib.refactoringdevelop.view.main

import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.presenter.IInteractor

class MainInteractor<T, R>(
    private val repositoryRemote: IRepository<T, R>,
    private val repositoryLocal: IRepository<T, R>
) : IInteractor<T, R> {

    override suspend fun getData(request: R, fromRemoteSource: Boolean): T {
        return if (fromRemoteSource) {
            val data = repositoryRemote.getData(request)
            repositoryLocal.setData(data)
            data
        } else {
            repositoryLocal.getData(request)
        }
    }

}