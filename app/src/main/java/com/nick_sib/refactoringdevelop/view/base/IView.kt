package com.nick_sib.refactoringdevelop.view.base

interface IView<T> {
    fun renderData(appState: T)
}