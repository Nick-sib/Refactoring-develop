package com.nick_sib.refactoringdevelop.view.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.nick_sib.refactoringdevelop.App
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.presenter.IInteractor
import com.nick_sib.refactoringdevelop.utils.network.isOnline
import com.nick_sib.refactoringdevelop.view.ui.AlertDialogFragment
import com.nick_sib.refactoringdevelop.viewmodel.BaseViewModel


abstract class BaseActivity<T : AppState, I : IInteractor<T>> : AppCompatActivity() {

    abstract val model: BaseViewModel<T>
    abstract fun hideLoadingDialog()

    protected var isNetworkAvailable: Boolean = false

    private var errorSnack: Snackbar? = null

    init {
        isNetworkAvailable = isOnline(App.instance)
    }

    protected fun showNoInternetConnectionDialog() {
        if (!isInternetWarning) {
            showAlertDialog(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            )
            isInternetWarning = true
        }
    }

    private fun showAlertDialog(title: String?, message: String?) {
        if (isDialogNull())
            AlertDialogFragment.newInstance(title, message).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean =
        supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null

    abstract fun renderData(dataModel: T)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
        var isInternetWarning: Boolean = false
            private set
    }

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