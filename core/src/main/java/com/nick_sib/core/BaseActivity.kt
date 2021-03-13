package com.nick_sib.core

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T, S> : AppCompatActivity() {

    abstract val model: BaseViewModel<T, S>
    abstract fun hideLoadingDialog()

    private var errorSnack: Snackbar? = null

    abstract fun renderData(dataModel: T)

    protected fun hideErrorDialog(){
        errorSnack?.dismiss()
    }

    protected fun showErrorDialog(view: View, messageText: Int?, buttonText: Int? = null){
        showErrorDialog(
            view,
            messageText?.let { getString(it) } ?: "Something went wrong",
            buttonText?.let { getString(it) } ?: "GOT IT")
    }

    protected fun showErrorDialog(view: View, messageText: String?, buttonText: String? = null){
        errorSnack = Snackbar.make(
            view,
            messageText ?: "Something went wrong",
            Snackbar.LENGTH_INDEFINITE
        ).setAction(buttonText ?: "GOT IT") {
            hideLoadingDialog()
        }.also {
            it.show()
        }
    }

}