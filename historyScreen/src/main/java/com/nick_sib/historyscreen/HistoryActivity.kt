package com.nick_sib.historyscreen

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.nick_sib.model.AppStateList
import com.nick_sib.model.ThrowableInternet
import com.nick_sib.core.BaseActivity
import com.nick_sib.core.adapter.MainAdapter
import com.nick_sib.historyscreen.databinding.ActivityHistoryBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryActivity: BaseActivity<AppStateList, String>() {

    private lateinit var binding: ActivityHistoryBinding
    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }

    override val model: HistoryViewModel by viewModel()

    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        model.subscribe().observe(this, { renderData(it) })

        adapter = MainAdapter { data ->
            startActivity(com.nick_sib.descriptionscreen.DescriptionActivity.getIntent(this, data))
        }

        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.rvSearchResult.addItemDecoration(itemDecorator)
        binding.rvSearchResult.adapter = adapter
    }

    override fun hideLoadingDialog() {
        loadDialog.visibility = View.GONE
    }

    private fun showLoadingIndocator(){
        loadDialog.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun renderData(dataModel: AppStateList) {
        when (dataModel) {
            is AppStateList.Success -> {
                hideLoadingDialog()
                hideErrorDialog()
                adapter.data = dataModel.data
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

}