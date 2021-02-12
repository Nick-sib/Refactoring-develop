package com.nick_sib.refactoringdevelop.presenter


import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.view.base.IView

interface IPresenter<T : AppState, V : IView<T>> {
    fun attachView(view: V)

    fun detachView()

    fun getData(word: String, isOnline: Boolean)
}