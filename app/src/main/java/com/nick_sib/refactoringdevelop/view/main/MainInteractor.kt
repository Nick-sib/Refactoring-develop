package com.nick_sib.refactoringdevelop.view.main

import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.presenter.IInteractor

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState =
        AppState
            .Success((if (fromRemoteSource) repositoryRemote else repositoryLocal)
            .getData(word))
}