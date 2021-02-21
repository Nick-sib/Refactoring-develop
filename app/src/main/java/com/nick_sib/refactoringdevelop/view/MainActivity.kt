package com.nick_sib.refactoringdevelop.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.model.ThrowableInternet
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.utils.network.isOnline
import com.nick_sib.refactoringdevelop.view.adapter.MainAdapter
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import com.nick_sib.refactoringdevelop.view.main.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    override lateinit var model: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isNetworkAvailable)
            showNoInternetConnectionDialog()

        val viewModel: MainViewModel by viewModel()
        model = viewModel

        model.subscribe().observe(this@MainActivity, { renderData(it) })

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.instance()
            searchDialogFragment.setOnSearchClickListener {
                isNetworkAvailable = isOnline(applicationContext)
                model.getData(it, isNetworkAvailable)
                if (!isNetworkAvailable)
                    showErrorDialog(
                        binding.root,
                        R.string.dialog_message_device_is_offline,
                        R.string.dialog_button_cancel)
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
                if (dataModel.error is ThrowableInternet)
                    showErrorDialog(
                        binding.root,
                        R.string.dialog_message_device_is_offline)
                else showErrorDialog(binding.root, dataModel.error.message, null)
            }
            is AppState.Loading -> {
                hideErrorDialog()
                showLoadingIndocator()
            }
        }
    }

    override fun hideLoadingDialog(){
        loadDialog.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
    }

    private fun showLoadingIndocator(){
        loadDialog.visibility = View.VISIBLE
        binding.searchFab.visibility = View.GONE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }
}