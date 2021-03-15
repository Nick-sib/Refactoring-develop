package com.nick_sib.descriptionscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.nick_sib.model.AppStateData
import com.nick_sib.model.DataModel
import com.nick_sib.model.ThrowableInternet
import com.nick_sib.core.BaseActivity
import com.nick_sib.descriptionscreen.databinding.ActivityDescriptionBinding
import org.koin.android.viewmodel.ext.android.viewModel


//TODO: Refactor to Fragment
class DescriptionActivity : BaseActivity<AppStateData, Long>() {

    private lateinit var binding: ActivityDescriptionBinding

    override val model: DescriptionViewModel by viewModel()

    private val loadDialog: View by lazy {
        binding.root.findViewById(R.id.v_load_dialog)
    }

    private lateinit var adapter: MeaningsViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        model.subscribe().observe(this, { renderData(it) })
        model.getData(intent.extras?.getLong(DATA_EXTRA) ?: -1)
        binding.handler = this

        binding.property = DataModel(-1,"", emptyList())
        binding.executePendingBindings()
        adapter = MeaningsViewPagerAdapter(emptyList(), supportFragmentManager)
        binding.vpTranslate.adapter = adapter
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

    fun doFavorite(){
        val newData: DataModel = binding.property?.let {
            it.copy(isFavorite = !it.isFavorite)
        } ?: DataModel(-1,"", emptyList() ,false)
        model.setData(AppStateData.Success(newData))
    }


    companion object {
        private const val DATA_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
        fun getIntent(
            context: Context,
            data: DataModel,
        ): Intent = Intent(context, DescriptionActivity::class.java)
            .apply { putExtra(DATA_EXTRA, data.id) }
    }

    override fun hideLoadingDialog() {
        loadDialog.visibility = View.GONE
    }

    override fun renderData(dataModel: AppStateData) {
        when (dataModel) {
            is AppStateData.Success -> {
                hideLoadingDialog()
                hideErrorDialog()
                binding.property = dataModel.data
                adapter.setData(dataModel.data.meanings)
                binding.executePendingBindings()
            }
            is AppStateData.Error -> {
                if (dataModel.error is ThrowableInternet)
                    showErrorDialog(
                        binding.root,
                        R.string.dialog_message_device_is_offline)
                else showErrorDialog(binding.root, dataModel.error.message, null)
            }
            is AppStateData.Loading -> {
                hideErrorDialog()
                showLoadingIndocator()
            }
        }
    }

    private fun showLoadingIndocator(){
        loadDialog.visibility = View.VISIBLE
    }
}