package com.nick_sib.refactoringdevelop.view


import android.os.Bundle
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.presenter.IPresenter
import com.nick_sib.refactoringdevelop.presenter.MainPresenterImpl
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.base.IView
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import java.util.*

class MainActivity : BaseActivity<AppState>() {

    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.instance()
            searchDialogFragment.setOnSearchClickListener {
                presenter.getData(it, true)
            }
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun createPresenter(): IPresenter<AppState, IView<AppState>> =
        MainPresenterImpl()

    override fun renderData(appState: AppState) {
        val data = when (appState) {
            is AppState.Success -> {
                appState.data?.let { it[0].text }
            }
            is AppState.Error -> TODO()
            is AppState.Loading -> TODO()
        }
        binding.tvInfo.text = data
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }


}