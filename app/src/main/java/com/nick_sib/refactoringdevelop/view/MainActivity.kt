package com.nick_sib.refactoringdevelop.view


import android.os.Bundle
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.presenter.IPresenter
import com.nick_sib.refactoringdevelop.presenter.MainPresenterImpl
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.base.IView

class MainActivity : BaseActivity<AppState>(R.layout.activity_main) {


    override fun renderData(appState: AppState) {

    }

    override fun createPresenter(): IPresenter<AppState, IView<AppState>> =
        MainPresenterImpl()



}