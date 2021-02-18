package com.nick_sib.refactoringdevelop.presenter

import com.nick_sib.refactoringdevelop.model.MainInteractorImpl
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.model.datasource.DataSourceLocal
import com.nick_sib.refactoringdevelop.model.datasource.DataSourceRemote
import com.nick_sib.refactoringdevelop.model.repository.RepositoryImpl
import com.nick_sib.refactoringdevelop.view.base.IView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenterImpl<T : AppState, V : IView<T>>(
    private val interactor: IInteractor<T> = MainInteractorImpl(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    private val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    private val schedulerIO: Scheduler = Schedulers.io(),
    private val schedulerUI: Scheduler = AndroidSchedulers.mainThread(),
): IPresenter<T, V> {

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView() {
        currentView = null
    }

    override fun getData(word: String, isOnline: Boolean) {

        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerIO)
                .observeOn(schedulerUI)
                .doOnSubscribe {
                    currentView?.renderData(AppState.Loading(null) as T)
                }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> =
        object : DisposableObserver<AppState>() {
            override fun onNext(appState: AppState) {
                currentView?.renderData(appState as T)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e) as T)
            }

            override fun onComplete() {}
        }

}