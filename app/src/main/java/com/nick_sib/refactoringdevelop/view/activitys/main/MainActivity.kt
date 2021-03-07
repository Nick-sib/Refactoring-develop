package com.nick_sib.refactoringdevelop.view.activitys.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityMainBinding
import com.nick_sib.refactoringdevelop.model.ThrowableInternet
import com.nick_sib.refactoringdevelop.model.data.AppStateList
import com.nick_sib.refactoringdevelop.view.activitys.description.DescriptionActivity
import com.nick_sib.refactoringdevelop.view.activitys.history.HistoryActivity
import com.nick_sib.refactoringdevelop.view.adapter.MainAdapter
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.fragment.SearchDialogFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity<AppStateList, String>() {

    override val model: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model.subscribe().observe(this@MainActivity, { renderData(it) })
        model.internetState.observe(this, { showInternetState(it) } )

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.instance()
            searchDialogFragment.setOnSearchClickListener {
                model.getData(it)
            }
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

        adapter = MainAdapter { data ->
            startActivity(DescriptionActivity.getIntent(this@MainActivity, data))
        }

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rvSearchResult.addItemDecoration(itemDecorator)

        binding.rvSearchResult.adapter = adapter
    }

    override fun renderData(dataModel: AppStateList) {
        when (dataModel) {
            is AppStateList.Success -> {
                hideLoadingDialog()
                hideErrorDialog()
                adapter.data = dataModel.data ?: emptyList()
            }
            is AppStateList.Error -> {
                if (dataModel.error is ThrowableInternet)
                    showErrorDialog(
                        binding.root,
                        R.string.dialog_message_device_is_offline)
                else showErrorDialog(binding.root, dataModel.error.message, null)
            }
            is AppStateList.Loading -> {
                hideErrorDialog()
                showLoadingIndocator()
            }
        }
    }

    private fun showInternetState(state: Boolean){
        if (state) hideErrorDialog() else
            showErrorDialog(
                binding.root,
                R.string.dialog_message_device_is_offline
            )
    }

    override fun hideLoadingDialog(){
        loadDialog.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
    }

    private fun showLoadingIndocator(){
        loadDialog.visibility = View.VISIBLE
        binding.searchFab.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "75b10273-329d-41ef-a433-bd69e20d0f06"
    }
}