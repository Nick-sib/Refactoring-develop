package com.nick_sib.refactoringdevelop.view.base

import com.nick_sib.refactoringdevelop.model.data.AppState

interface IView<T: AppState> {
    fun renderData(appState: T)
}