package com.nick_sib.refactoringdevelop.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.utils.network.isOnline
import com.nick_sib.refactoringdevelop.view.adapter.MainAdapter
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import com.nick_sib.refactoringdevelop.view.main.MainViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    override lateinit var model: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }
    private lateinit var adapter: MainAdapter
    private var errorSnack: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = viewModelFactory.create(MainViewModel::class.java)
        model.subscribe().observe(this@MainActivity) { renderData(it) }

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.instance()
            searchDialogFragment.setOnSearchClickListener {
                isNetworkAvailable = isOnline(applicationContext)
                model.getData(it, isNetworkAvailable)
                if (!isNetworkAvailable)
                    showNoInternetConnectionDialog()
            }
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
        adapter = MainAdapter {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.rvSearchResult.adapter = adapter
    }

    override fun renderData(dataModel: AppState) {
        when (dataModel) {
            is AppState.Success -> {
                hideLoadingDialog()
                hideErrorDialog()
                adapter.data = dataModel.data ?: emptyList()
            }
            is AppState.Error -> {
                showErrorDialog(dataModel.error.message)
            }
            is AppState.Loading -> {
                hideErrorDialog()
                showLoadingIndocator()
            }
        }
    }

    private fun hideLoadingDialog(){
        loadDialog.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
    }

    private fun hideErrorDialog(){
        errorSnack?.dismiss()
    }

    private fun showLoadingIndocator(){
        loadDialog.visibility = View.VISIBLE
        binding.searchFab.visibility = View.GONE
    }

    private fun showErrorDialog(text: String?){
        errorSnack = Snackbar.make(
            binding.root,
            text ?: "Что - пошло не так",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Прервать") {
                hideLoadingDialog()
        }.also {
            it.show()
        }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }
}