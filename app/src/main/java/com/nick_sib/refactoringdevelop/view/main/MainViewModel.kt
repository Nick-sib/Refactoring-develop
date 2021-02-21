package com.nick_sib.refactoringdevelop.view.main

import androidx.lifecycle.LiveData
import com.nick_sib.refactoringdevelop.App
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.utils.network.isOnline
import com.nick_sib.refactoringdevelop.utils.parseSearchResults
import javax.inject.Inject
import com.nick_sib.refactoringdevelop.viewmodel.BaseViewModel
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel @Inject constructor(private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = AppState.Loading(null) }

//    private fun getObserverEE(word: String, isOnline: Boolean): Single<AppState> =
//        interactor.getData(word, isOnline){
//
//        }



//        .subscribeWith(getObserver())




    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            override fun onNext(state: AppState) {
                appState = parseSearchResults(state)
                liveDataForViewToObserve.value = appState
            }
            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }
            override fun onComplete() {}
        }
    }
}
