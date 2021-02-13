package com.nick_sib.refactoringdevelop.presenter

import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.view.base.IView

class MainPresenterImpl<T : AppState, V : IView<T>>: IPresenter<T, V> {

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
        val data = AppState.Success(listOf(DataModel(text =  word, meanings = null))) as AppState
//        val data: AppState = AppState.Loading(progress = 10)
        currentView?.renderData(data as T)
    }
}