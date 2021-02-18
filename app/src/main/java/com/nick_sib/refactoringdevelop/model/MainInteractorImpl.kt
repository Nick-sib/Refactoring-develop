package com.nick_sib.refactoringdevelop.model

import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.model.repository.IRepository
import com.nick_sib.refactoringdevelop.presenter.IInteractor
import io.reactivex.rxjava3.core.Observable


class MainInteractorImpl<T : AppState>(
    private val remoteRepository: IRepository<List<DataModel>>,
    private val localRepository: IRepository<List<DataModel>>
) : IInteractor<T> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<T> =
        if (fromRemoteSource)
            remoteRepository.getData(word).map { AppState.Success(it) as T}
        else
            localRepository.getData(word).map{ AppState.Success(it) as T}


}