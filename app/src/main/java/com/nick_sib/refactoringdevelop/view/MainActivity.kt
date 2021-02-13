package com.nick_sib.refactoringdevelop.view


import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.presenter.IPresenter
import com.nick_sib.refactoringdevelop.presenter.MainPresenterImpl
import com.nick_sib.refactoringdevelop.view.adapter.MainAdapter
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.base.IView
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment

class MainActivity : BaseActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }
    private lateinit var adapter: MainAdapter


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
        adapter = MainAdapter(){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.rvSearchResult.adapter = adapter
    }

    override fun createPresenter(): IPresenter<AppState, IView<AppState>> =
        MainPresenterImpl()

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                hideLoadingDialog()
                adapter.data = appState.data ?: emptyList()
            }
            is AppState.Error -> TODO()
            is AppState.Loading -> { showLoadingDialog(); "Load" }
        }
    }

    private fun showLoadingDialog(){
        loadDialog.visibility = View.VISIBLE
        binding.searchFab.visibility = View.GONE
    }

    private fun hideLoadingDialog(){
        loadDialog.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG  = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }


}