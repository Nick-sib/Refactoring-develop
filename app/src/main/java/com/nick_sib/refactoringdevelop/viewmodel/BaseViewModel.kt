package com.nick_sib.refactoringdevelop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.rx.SchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable


abstract class BaseViewModel<T : AppState>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
    protected open val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected open val schedulerProvider: SchedulerProvider = SchedulerProvider()
) : ViewModel() {

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}