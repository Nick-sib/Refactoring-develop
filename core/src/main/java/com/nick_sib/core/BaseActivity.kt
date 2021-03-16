package com.nick_sib.core

import android.content.Intent
import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nick_sib.model.ThrowableInternet
import android.provider.Settings

abstract class BaseActivity<T, S> : AppCompatActivity() {

    abstract val model: BaseViewModel<T, S>
    abstract fun hideLoadingDialog()

    private var errorSnack: Snackbar? = null

    abstract fun renderData(dataModel: T)

    protected fun hideErrorDialog(){
        errorSnack?.dismiss()
    }

    private fun showErrorInternet(view: View) {
        errorSnack = Snackbar.make(
            view,
            R.string.dialog_message_device_is_offline,
            Snackbar.LENGTH_INDEFINITE
        ).setAction("SET") {

        }.also {
            it.show()
        }
    }

    protected fun showErrorDialog(view: View, error: Throwable){
        if (error is ThrowableInternet) {
            errorSnack = Snackbar.make(
                view,
                R.string.dialog_message_device_is_offline,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.err_internet_button) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startActivityForResult(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY), 42)
                } else {
                    hideLoadingDialog()
                }
            }.also {
                it.show()
            }
        } else {
            showErrorDialog(view, error.message, null)
        }
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