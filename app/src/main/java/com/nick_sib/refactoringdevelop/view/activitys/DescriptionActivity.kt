package com.nick_sib.refactoringdevelop.view.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.databinding.ActivityDescriptionBinding
import com.nick_sib.refactoringdevelop.model.data.AppStateData
import com.nick_sib.refactoringdevelop.model.data.AppStateList
import com.nick_sib.refactoringdevelop.model.data.DataModel
import com.nick_sib.refactoringdevelop.view.adapter.MeaningsViewPagerAdapter
import com.nick_sib.refactoringdevelop.view.base.BaseActivity
import com.nick_sib.refactoringdevelop.view.main.MainInteractor
import com.nick_sib.refactoringdevelop.viewmodel.BaseViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


//TODO: Refactor to Fragment
class DescriptionActivity : BaseActivity<AppStateData, String, MainInteractor<AppStateData, String>>() {//: AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private lateinit var data: DataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        data = intent.extras?.getParcelable(DATA_EXTRA) ?: DataModel(-1, null, null)
        binding.property = data
        binding.handler = this
        binding.executePendingBindings()
        binding.vpTranslate.adapter = MeaningsViewPagerAdapter(data.meanings ?: emptyList(),supportFragmentManager)
        //only for commit
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
        binding.property = data.apply {
            isFavorite = !isFavorite
            Toast.makeText(this@DescriptionActivity,
                getString(if (isFavorite) R.string.toast_is_favorite else R.string.toast_is_not_favorite,
                    text?.toUpperCase(Locale.ROOT) ?: ""),
                Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val DATA_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
        fun getIntent(
            context: Context,
            data: DataModel,
        ): Intent = Intent(context, DescriptionActivity::class.java)
            .apply { putExtra(DATA_EXTRA, data) }
    }

    override val model: BaseViewModel<AppStateData> by viewModel()

    override fun hideLoadingDialog() {
        TODO("Not yet implemented")
    }

    override fun renderData(dataModel: AppStateData) {
        TODO("Not yet implemented")
    }
}