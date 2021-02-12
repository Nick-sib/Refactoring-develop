package com.nick_sib.refactoringdevelop.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.presenter.IPresenter

abstract class BaseActivity<T : AppState>(
        @LayoutRes layoutResID: Int
): AppCompatActivity(layoutResID), IView<T> {

    private lateinit var presenter: IPresenter<T, IView<T>>

    protected abstract fun createPresenter(): IPresenter<T, IView<T>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}