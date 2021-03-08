package com.nick_sib.refactoringdevelop.view.activitys.main

import com.nick_sib.repository.repository.IRepository

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

    override suspend fun saveData(data: T): Boolean {
        repositoryLocal.setData(data)
        return true
    }

}