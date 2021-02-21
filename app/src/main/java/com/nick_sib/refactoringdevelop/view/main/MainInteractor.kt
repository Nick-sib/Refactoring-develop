package com.nick_sib.refactoringdevelop.view.main

import com.nick_sib.refactoringdevelop.di.NAME_LOCAL
import com.nick_sib.refactoringdevelop.di.NAME_REMOTE
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.presenter.IInteractor
import io.reactivex.rxjava3.core.Observable

import javax.inject.Inject
import javax.inject.Named

class MainInteractor @Inject constructor(
    @Named(NAME_REMOTE) val repositoryRemote: IRepository<List<DataModel>>,
    @Named(NAME_LOCAL) val repositoryLocal: IRepository<List<DataModel>>
) : IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> =
        (if (fromRemoteSource) { repositoryRemote } else { repositoryLocal })
            .getData(word).map { AppState.Success(it) }

}